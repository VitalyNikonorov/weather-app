package net.nikonorov.weather.data

import net.nikonorov.weather.model.Location

interface LocationRepository {
    suspend fun getLocations(): List<Location>
    suspend fun getLocation(id: String): Location?
}
