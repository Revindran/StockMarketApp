package com.rar.devs.stockmarketapp.data.mappers

import com.rar.devs.stockmarketapp.data.local.CompanyListingEntity
import com.rar.devs.stockmarketapp.data.remote.dto.CompanyInfoDto
import com.rar.devs.stockmarketapp.domain.model.CompanyInfo
import com.rar.devs.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol ?: "",
        description ?: "",
        name ?: "",
        country ?: "",
        industry ?: ""
    )
}

fun CompanyInfo.toCompanyInfoDto(): CompanyInfoDto {
    return CompanyInfoDto(
        symbol, description, name, country, industry
    )
}
