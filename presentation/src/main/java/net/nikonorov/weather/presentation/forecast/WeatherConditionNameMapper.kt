package net.nikonorov.weather.presentation.forecast

import net.nikonorov.weather.model.WeatherCondition
import net.nikonorov.weather.resources.StringWrapper
import net.nikonorov.weather.resources.Strings

/**
 * Mapper class for mapping WeatherCondition: Domain -> Ui State
 */
class WeatherConditionNameMapper {
    fun getConditionName(condition: WeatherCondition): StringWrapper? {
        val resourceId = when (condition) {
            WeatherCondition.UNKNOWN -> null
            WeatherCondition.CLEAR_SKY -> Strings.ClearSky
            WeatherCondition.PARTLY_CLOUDY -> Strings.Cloudy
            WeatherCondition.FOG -> Strings.Fog
            WeatherCondition.DRIZZLE -> Strings.Drizzle
            WeatherCondition.FREEZING_DRIZZLE -> Strings.Drizzle
            WeatherCondition.RAIN -> Strings.Rain
            WeatherCondition.FREEZING_RAIN -> Strings.Rain
            WeatherCondition.SNOWFALL -> Strings.Snow
            WeatherCondition.SNOW_GRAINS -> Strings.Snow
            WeatherCondition.RAIN_SHOWERS -> Strings.Rain
            WeatherCondition.SNOW_SHOWERS -> Strings.Snow
            WeatherCondition.THUNDERSTORM -> Strings.Thunderstorm
            WeatherCondition.THUNDERSTORM_WITH_HAIL -> Strings.Thunderstorm
        }
        return resourceId?.let { StringWrapper.from(it) }
    }
}
