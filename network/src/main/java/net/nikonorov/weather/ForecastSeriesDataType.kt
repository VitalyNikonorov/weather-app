package net.nikonorov.weather

/**
 * Data types supported by OpenMeteoApi
 */
enum class ForecastSeriesDataType(val key: String) {
    TEMPERATURE("temperature_2m"),
    HUMIDITY("relativehumidity_2m"),
    PRECIPITATION("precipitation"),
    WIND_SPEED("windspeed_10m"),
    WEATHER_CODE("weathercode"),
}
