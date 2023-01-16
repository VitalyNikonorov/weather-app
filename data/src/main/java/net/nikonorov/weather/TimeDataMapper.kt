package net.nikonorov.weather

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * DateTime data parser
 */
class TimeDataMapper {

    private val pattern = "yyyy-MM-dd'T'HH:mm"

    fun map(dateTimeItems: List<String?>?, timezone: String?): List<ZonedDateTime>? {
        if (timezone == null) return null
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val zone = ZoneId.of(timezone)
        return dateTimeItems?.map {
            if (it == null) return null
            LocalDateTime.parse(it, formatter).atZone(zone) ?: return null
        }
    }
}
