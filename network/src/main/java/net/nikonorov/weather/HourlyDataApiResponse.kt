package net.nikonorov.weather

import com.google.gson.annotations.SerializedName

/**
 * DTO of the hourly weather data supported by OpenMeteoApi
 */
class HourlyDataApiResponse {
    @SerializedName("time")
    var time: List<String?>? = null

    @SerializedName("temperature_2m")
    var temperature: List<Double?>? = null

    @SerializedName("relativehumidity_2m")
    var humidity: List<Int?>? = null

    @SerializedName("precipitation")
    var precipitation: List<Double?>? = null

    @SerializedName("windspeed_10m")
    var windSpeed: List<Double?>? = null

    @SerializedName("weathercode")
    var weatherCode: List<Int?>? = null
}
