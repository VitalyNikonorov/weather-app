package net.nikonorov.weather.presentation.forecast

import net.nikonorov.weather.model.WeatherData
import net.nikonorov.weather.resources.StringWrapper
import net.nikonorov.weather.resources.Strings
import java.time.ZonedDateTime

/**
 * Mapper class of the Forecast Ui State. Domain -> Ui
 */
class ForecastUiStateMapper(private val conditionNameMapper: WeatherConditionNameMapper) {

    fun map(data: WeatherData, locationName: String): ForecastSuccessState {
        val now = ZonedDateTime.now(data.temperature.data[0].time.zone)
        val minTemperature = data.temperature.data.filter { it.time.dayOfYear == now.dayOfYear }
            .minBy { it.value }.value
        val maxTemperature = data.temperature.data.filter { it.time.dayOfYear == now.dayOfYear }
            .maxBy { it.value }.value
        val curTemp =
            data.temperature.data.first { it.time.dayOfYear == now.dayOfYear && it.time.hour == now.hour }
        val currentCondition =
            data.weatherCondition.data.first { it.time.dayOfYear == now.dayOfYear && it.time.hour == now.hour }
        val temperatureRange = StringWrapper.from(
            Strings.TemperatureRange,
            maxTemperature.toInt(),
            data.temperature.units,
            minTemperature.toInt(),
            data.temperature.units
        )
        return ForecastSuccessState(
            locationName = locationName,
            currentTemperature = StringWrapper.from(
                Strings.CurrentTemperatureFormat, curTemp.value.toInt(), data.temperature.units
            ),
            weatherCondition = conditionNameMapper.getConditionName(currentCondition.value),
            temperatureRange = temperatureRange,
        )
    }
}