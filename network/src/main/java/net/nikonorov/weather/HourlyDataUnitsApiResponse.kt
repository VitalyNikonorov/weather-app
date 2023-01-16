package net.nikonorov.weather

import com.google.gson.annotations.SerializedName

/**
 * DTO of the Data Units of weather data
 */
class HourlyDataUnitsApiResponse {
    @SerializedName("time")
    var time: String? = null

    @SerializedName("temperature_2m")
    var temperature: String? = null

    @SerializedName("relativehumidity_2m")
    var humidity: String? = null

    @SerializedName("precipitation")
    var precipitation: String? = null

    @SerializedName("windspeed_10m")
    var windSpeed: String? = null

    @SerializedName("weathercode")
    var weatherCode: String? = null
}
