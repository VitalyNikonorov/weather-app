package net.nikonorov.weather.model

import java.time.ZonedDateTime

/**
 * Generic domain model of the time series data
 */
data class TimeSeriesData<T>(
    val time: ZonedDateTime,
    val value: T,
)
