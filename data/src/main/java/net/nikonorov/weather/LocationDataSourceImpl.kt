package net.nikonorov.weather

import net.nikonorov.weather.model.Coordinate
import net.nikonorov.weather.model.Location

/**
 * Simple In-Memory implementation of the Locations data source
 */
class LocationDataSourceImpl : LocationDataSource {
    private val supportedLocations = listOf(
        SYDNEY,
        TOKYO,
        SINGAPORE,
        BEIJING,
        BANGKOK,
        ANZHERO_SUDZHENSK,
        MOSCOW,
        PARIS,
        LONDON,
        NEW_YORK,
        MOUNTAIN_VIEW,
        SAN_FRANCISCO
    )
    private val locationMap = supportedLocations.associateBy { it.id }

    override fun getLocations(): List<Location> {
        return supportedLocations
    }

    override fun getLocation(id: String): Location? {
        return locationMap[id]
    }

    companion object {
        val SYDNEY = Location("0", "Sydney", Coordinate(-33.87, 151.21), "Australia/Sydney")
        val TOKYO = Location("1", "Tokyo", Coordinate(35.69, 139.69), "Asia/Tokyo")
        val SINGAPORE = Location("2", "Singapore", Coordinate(1.29, 103.85), "Asia/Singapore")
        val BEIJING = Location("3", "Beijing", Coordinate(39.91, 116.40), "Asia/Shanghai")
        val BANGKOK = Location("4", "Bangkok", Coordinate(13.75, 100.50), "Asia/Bangkok")
        val ANZHERO_SUDZHENSK =
            Location("5", "Anzhero-Sudzhensk", Coordinate(56.08, 86.03), "Asia/Novosibirsk")
        val MOSCOW = Location("6", "Moscow", Coordinate(55.75, 37.62), "Europe/Moscow")
        val PARIS = Location("7", "Paris", Coordinate(48.85, 2.35), "Europe/Paris")
        val LONDON = Location("8", "London", Coordinate(51.51, -0.13), "Europe/London")
        val NEW_YORK = Location("9", "New York", Coordinate(40.71, -74.01), "America/New_York")
        val MOUNTAIN_VIEW =
            Location("10", "Mountain View", Coordinate(37.39, -122.08), "America/Los_Angeles")
        val SAN_FRANCISCO =
            Location("11", "San Francisco", Coordinate(37.77, -122.42), "America/Los_Angeles")
    }
}