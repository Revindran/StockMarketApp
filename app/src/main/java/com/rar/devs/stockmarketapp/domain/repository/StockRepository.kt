package com.rar.devs.stockmarketapp.domain.repository

import com.rar.devs.stockmarketapp.domain.model.CompanyInfo
import com.rar.devs.stockmarketapp.domain.model.CompanyListing
import com.rar.devs.stockmarketapp.domain.model.IntraDayInfo
import com.rar.devs.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo>

}