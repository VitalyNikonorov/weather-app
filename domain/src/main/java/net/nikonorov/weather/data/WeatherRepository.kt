package net.nikonorov.weather.data

import net.nikonorov.weather.model.Location
import net.nikonorov.weather.model.WeatherData

interface WeatherRepository {
    suspend fun fetchWeatherData(location: Location): Result<WeatherData>
}
