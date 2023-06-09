package com.rar.devs.stockmarketapp.data.mappers

import com.rar.devs.stockmarketapp.data.remote.dto.IntraDayInfoDto
import com.rar.devs.stockmarketapp.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun IntraDayInfoDto.toIntraDayInfo(): IntraDayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntraDayInfo(date = localDateTime, close = close)
}