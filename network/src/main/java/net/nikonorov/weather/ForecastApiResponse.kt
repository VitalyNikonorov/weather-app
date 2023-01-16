package net.nikonorov.weather

import com.google.gson.annotations.SerializedName

/**
 * Forecast data DTO used by OpenMeteoApi
 */
class ForecastApiResponse {
    @SerializedName("latitude")
    var lat: Double? = null

    @SerializedName("longitude")
    var lon: Double? = null

    @SerializedName("generationtime_ms")
    var generationTimeMillis: Double? = null

    @SerializedName("utc_offset_seconds")
    var utcOffsetSeconds: Int? = null

    @SerializedName("timezone")
    var timezone: String? = null

    @SerializedName("timezone_abbreviation")
    var timezoneAbbreviation: String? = null

    @SerializedName("elevation")
    var elevation: Double? = null

    @SerializedName("hourly_units")
    var hourlyUnits: HourlyDataUnitsApiResponse? = null

    @SerializedName("hourly")
    var hourlyData: HourlyDataApiResponse? = null
}
