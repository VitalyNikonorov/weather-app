package net.nikonorov.weather

import net.nikonorov.weather.model.TimeSeriesData
import net.nikonorov.weather.model.WeatherCondition
import java.time.ZonedDateTime


/**
 * Mapper of the [WeatherCondition] from DTO to domain model
 */
class WeatherConditionMapper {
    fun map(code: Int): WeatherCondition {
        return codeMap[code] ?: WeatherCondition.UNKNOWN
    }

    fun mapSeries(
        data: List<Int?>?, time: List<ZonedDateTime>
    ): List<TimeSeriesData<WeatherCondition>>? {
        if (data?.size != time.size) return null
        return time.zip(data).map { pair ->
            val value = pair.second?.let { map(it) } ?: return null
            TimeSeriesData(pair.first, value)
        }
    }

    companion object {
        val codeMap = listOf(
            setOf(0).map { it to WeatherCondition.CLEAR_SKY },
            setOf(1, 2, 3).map { it to WeatherCondition.PARTLY_CLOUDY },
            setOf(45, 48).map { it to WeatherCondition.FOG },
            setOf(51, 53, 55).map { it to WeatherCondition.DRIZZLE },
            setOf(56, 57).map { it to WeatherCondition.FREEZING_DRIZZLE },
            setOf(61, 63, 65).map { it to WeatherCondition.RAIN },
            setOf(66, 67).map { it to WeatherCondition.FREEZING_RAIN },
            setOf(71, 73, 75).map { it to WeatherCondition.SNOWFALL },
            setOf(77).map { it to WeatherCondition.SNOW_GRAINS },
            setOf(80, 81, 82).map { it to WeatherCondition.RAIN_SHOWERS },
            setOf(85, 86).map { it to WeatherCondition.SNOW_SHOWERS },
            setOf(95).map { it to WeatherCondition.THUNDERSTORM },
            setOf(96, 99).map { it to WeatherCondition.THUNDERSTORM_WITH_HAIL },
        ).flatten().toMap()
    }
}
