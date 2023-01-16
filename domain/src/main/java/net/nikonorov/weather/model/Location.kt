package net.nikonorov.weather.model

/**
 * Domain model of a geographical location
 */
data class Location(
    val id: String,
    val name: String,
    val coordinate: Coordinate,
    val timezone: String,
)
