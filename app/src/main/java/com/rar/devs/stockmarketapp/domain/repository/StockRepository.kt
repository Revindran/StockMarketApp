package com.rar.devs.stockmarketapp.domain.repository

import com.rar.devs.stockmarketapp.domain.model.CompanyListing
import com.rar.devs.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>>

}