package net.nikonorov.weather.resources

/**
 * Domain representation of string resources.
 * Mapping is in StringResolver file
 */
sealed interface Strings {

    object ClearSky : Strings
    object Cloudy : Strings
    object Fog : Strings
    object Drizzle : Strings
    object Rain : Strings
    object Snow : Strings
    object Thunderstorm : Strings
    object TemperatureRange : Strings
    object CurrentTemperatureFormat : Strings
    object SelectLocationMessage : Strings
}
