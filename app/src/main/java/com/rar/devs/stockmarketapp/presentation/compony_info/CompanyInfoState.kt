package com.rar.devs.stockmarketapp.presentation.compony_info

import com.rar.devs.stockmarketapp.domain.model.CompanyInfo
import com.rar.devs.stockmarketapp.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stocksInfo: List<IntraDayInfo>? = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
