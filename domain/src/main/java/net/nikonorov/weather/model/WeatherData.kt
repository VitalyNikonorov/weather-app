package net.nikonorov.weather.model

/**
 * Domain model os the weather data
 */
data class WeatherData(
    val lat: Double,
    val lng: Double,
    val timezone: String,
    val timezoneAbbreviation: String,
    val elevation: Double,
    val temperature: WeatherDataSeries<Double>,
    val humidity: WeatherDataSeries<Int>,
    val precipitation: WeatherDataSeries<Double>,
    val windSpeed: WeatherDataSeries<Double>,
    val weatherCondition: WeatherDataSeries<WeatherCondition>,
)
