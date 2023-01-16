package net.nikonorov.weather

import net.nikonorov.weather.model.TimeSeriesData
import java.time.ZonedDateTime

/**
 * Generic mapper for [TimeSeriesData] data types
 */
class TimeSeriesMapper<T> {
    fun map(data: List<T?>?, time: List<ZonedDateTime>): List<TimeSeriesData<T>>? {
        if (data?.size != time.size) return null
        return time.zip(data).map { pair ->
            val value = pair.second ?: return null
            TimeSeriesData(pair.first, value)
        }
    }
}
