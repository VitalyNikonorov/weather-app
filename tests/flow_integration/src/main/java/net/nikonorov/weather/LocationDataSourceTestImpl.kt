package net.nikonorov.weather

import net.nikonorov.weather.TestConst.testLocation1
import net.nikonorov.weather.TestConst.testLocation2
import net.nikonorov.weather.model.Location

class LocationDataSourceTestImpl : LocationDataSource {
    private val locations = listOf(testLocation1, testLocation2)

    override fun getLocations(): List<Location> {
        return locations
    }

    override fun getLocation(id: String): Location? {
        return locations.firstOrNull { it.id == id }
    }
}