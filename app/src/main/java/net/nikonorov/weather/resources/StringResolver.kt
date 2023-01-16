package net.nikonorov.weather.resources

import androidx.annotation.StringRes
import net.nikonorov.weather.R

/**
 * Utility file with string resources resolving
 */
@StringRes
fun Strings.resolve(): Int {
    return when (this) {
        Strings.ClearSky -> R.string.weather_condition_clear_sky
        Strings.Cloudy -> R.string.weather_condition_cloudy
        Strings.Drizzle -> R.string.weather_condition_drizzle
        Strings.Fog -> R.string.weather_condition_fog
        Strings.Rain -> R.string.weather_condition_rain
        Strings.Snow -> R.string.weather_condition_snow
        Strings.Thunderstorm -> R.string.weather_condition_thunderstorm
        Strings.TemperatureRange -> R.string.temperature_range
        Strings.CurrentTemperatureFormat -> R.string.temperature_format
        Strings.SelectLocationMessage -> R.string.select_location_message
    }
}
