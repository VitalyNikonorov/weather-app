package net.nikonorov.weather

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("/v1/forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("timezone") timezone: String,
        @Query("hourly") data: List<String>,
    ): Result<ForecastApiResponse>
}
