package net.nikonorov.weather

import net.nikonorov.weather.model.Location

/**
 * Provider of the Locations data
 */
interface LocationDataSource {
    fun getLocations(): List<Location>
    fun getLocation(id: String): Location?
}
