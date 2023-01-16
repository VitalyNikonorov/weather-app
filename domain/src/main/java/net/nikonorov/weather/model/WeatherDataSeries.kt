package net.nikonorov.weather.model

/**
 * [TimeSeriesData] of the weather data
 */
data class WeatherDataSeries<T>(
    val data: List<TimeSeriesData<T>>,
    val units: String
)
