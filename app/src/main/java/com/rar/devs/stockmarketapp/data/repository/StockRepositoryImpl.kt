package com.rar.devs.stockmarketapp.data.repository

import com.rar.devs.stockmarketapp.data.csv.CSVParser
import com.rar.devs.stockmarketapp.data.local.StockDatabase
import com.rar.devs.stockmarketapp.data.mappers.toCompanyInfo
import com.rar.devs.stockmarketapp.data.mappers.toCompanyListing
import com.rar.devs.stockmarketapp.data.mappers.toCompanyListingEntity
import com.rar.devs.stockmarketapp.data.remote.StockApi
import com.rar.devs.stockmarketapp.domain.model.CompanyInfo
import com.rar.devs.stockmarketapp.domain.model.CompanyListing
import com.rar.devs.stockmarketapp.domain.model.IntraDayInfo
import com.rar.devs.stockmarketapp.domain.repository.StockRepository
import com.rar.devs.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intraDayInfoParser: CSVParser<IntraDayInfo>,
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(data = localListings.map { it.toCompanyListing() }))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListing = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Unable to load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Http error"))
                null
            }

            remoteListing?.let { listings ->
                dao.clearCompanyListing()
                dao.insertCompanyListings(listings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(dao.searchCompanyListing("").map { it.toCompanyListing() }))
                emit(Resource.Loading(false))
            }

        }
    }

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val resposne = api.getIntradayInfo(symbol)
            val results = intraDayInfoParser.parse(resposne.byteStream())
            Resource.Success(results)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Resource.Error("could not load intraday info")
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("could not load intraday info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyInfo(symbol)
            Resource.Success(response.toCompanyInfo())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Resource.Error("could not load company info")
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("could not load company info")
        }
    }
}






















