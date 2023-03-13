package com.rar.devs.stockmarketapp.presentation.company_listing

sealed class CompanyListingsEvent {
    object Refresh : CompanyListingsEvent()
    data class SearchQuery(val query: String) : CompanyListingsEvent()
}
