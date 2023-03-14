package com.rar.devs.stockmarketapp.di

import com.rar.devs.stockmarketapp.data.csv.CSVParser
import com.rar.devs.stockmarketapp.data.csv.CompanyListingParser
import com.rar.devs.stockmarketapp.data.csv.IntraDayInfoParser
import com.rar.devs.stockmarketapp.data.repository.StockRepositoryImpl
import com.rar.devs.stockmarketapp.domain.model.CompanyListing
import com.rar.devs.stockmarketapp.domain.model.IntraDayInfo
import com.rar.devs.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingParser: CompanyListingParser,
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntraDayInfoParser(
        intraDayInfoParser: IntraDayInfoParser,
    ): CSVParser<IntraDayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

}