package com.rar.devs.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.rar.devs.stockmarketapp.data.mappers.toIntraDayInfo
import com.rar.devs.stockmarketapp.data.remote.dto.IntraDayInfoDto
import com.rar.devs.stockmarketapp.domain.model.IntraDayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntraDayInfoParser @Inject constructor() : CSVParser<IntraDayInfo> {
    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader.readAll().drop(1).mapNotNull { line ->
                val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                val close = line.getOrNull(4) ?: return@mapNotNull null
                val dto = IntraDayInfoDto(
                    timestamp = timestamp,
                    close = close.toDouble()
                )
                dto.toIntraDayInfo()
            }
                .filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also { csvReader.close() }
        }
    }
}