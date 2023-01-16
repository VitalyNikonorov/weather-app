package net.nikonorov.weather

import net.nikonorov.weather.model.WeatherData
import net.nikonorov.weather.model.WeatherDataSeries

/**
 * Mapper of Weather data DTO to domain model
 */
class ForecastDataMapper(
    private val timeDataMapper: TimeDataMapper = TimeDataMapper(),
    private val intDataMapper: TimeSeriesMapper<Int> = TimeSeriesMapper(),
    private val doubleDataMapper: TimeSeriesMapper<Double> = TimeSeriesMapper(),
    private val conditionMapper: WeatherConditionMapper = WeatherConditionMapper(),
) {

    fun map(data: ForecastApiResponse?): WeatherData? {
        val timeData = timeDataMapper.map(data?.hourlyData?.time, data?.timezone) ?: return null
        val temperature =
            doubleDataMapper.map(data?.hourlyData?.temperature, timeData) ?: return null
        val humidity = intDataMapper.map(data?.hourlyData?.humidity, timeData) ?: return null
        val precipitation =
            doubleDataMapper.map(data?.hourlyData?.precipitation, timeData) ?: return null
        val windSpeed = doubleDataMapper.map(data?.hourlyData?.windSpeed, timeData) ?: return null
        val weatherCondition =
            conditionMapper.mapSeries(data?.hourlyData?.weatherCode, timeData) ?: return null

        return WeatherData(
            lat = data?.lat ?: return null,
            lng = data.lon ?: return null,
            timezone = data.timezone ?: return null,
            timezoneAbbreviation = data.timezoneAbbreviation ?: return null,
            elevation = data.elevation ?: return null,
            temperature = WeatherDataSeries(
                temperature, data.hourlyUnits?.temperature ?: return null
            ),
            humidity = WeatherDataSeries(
                humidity, data.hourlyUnits?.humidity ?: return null
            ),
            precipitation = WeatherDataSeries(
                precipitation, data.hourlyUnits?.precipitation ?: return null
            ),
            windSpeed = WeatherDataSeries(
                windSpeed, data.hourlyUnits?.windSpeed ?: return null
            ),
            weatherCondition = WeatherDataSeries(
                weatherCondition, data.hourlyUnits?.weatherCode ?: return null
            )
        )
    }
}
