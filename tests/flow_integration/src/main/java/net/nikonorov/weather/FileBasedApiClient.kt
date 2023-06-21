package net.nikonorov.weather

import com.google.gson.Gson
import java.io.FileReader


class FileBasedApiClient : OpenMeteoApi {
    private val gson = Gson()
    private var filePath: String? = null

    fun setupFilePath(path: String) {
        filePath = path
    }

    override suspend fun getWeatherForecast(
        lat: Double,
        lng: Double,
        timezone: String,
        data: List<String>,
    ): Result<ForecastApiResponse> {
        filePath?.let {
            val response: ForecastApiResponse = gson.fromJson(
                FileReader(filePath),
                ForecastApiResponse::class.java
            )
            return Result.success(response)
        }
        throw IllegalStateException("FileBasedApiClient is not setup")
    }
}
