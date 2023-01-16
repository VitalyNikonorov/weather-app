package net.nikonorov.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.nikonorov.weather.data.WeatherRepository
import net.nikonorov.weather.model.Location
import net.nikonorov.weather.model.WeatherData

/**
 * Implementation of the [WeatherRepository]
 */
class WeatherRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val openMeteoApi: OpenMeteoApi,
    private val dataMapper: ForecastDataMapper,
) : WeatherRepository {

    private val fullForecastSeriesDataSet = listOf(
        ForecastSeriesDataType.TEMPERATURE,
        ForecastSeriesDataType.HUMIDITY,
        ForecastSeriesDataType.PRECIPITATION,
        ForecastSeriesDataType.WIND_SPEED,
        ForecastSeriesDataType.WEATHER_CODE,
    ).map { it.key }

    override suspend fun fetchWeatherData(location: Location): Result<WeatherData> {
        return withContext(ioDispatcher) {
            openMeteoApi.getWeatherForecast(
                location.coordinate.lat,
                location.coordinate.lng,
                location.timezone,
                fullForecastSeriesDataSet
            ).fold(onSuccess = { response ->
                dataMapper.map(response)?.let { Result.success(it) } ?: Result.failure(
                    IllegalArgumentException()
                )
            }, onFailure = { Result.failure(it) })
        }
    }
}
