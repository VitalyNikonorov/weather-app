package net.nikonorov.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.nikonorov.weather.data.LocationRepository
import net.nikonorov.weather.model.Location

/**
 * Repository class that encapsulates Location data logic
 */
class LocationRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val locationDataSource: LocationDataSource,
) : LocationRepository {

    override suspend fun getLocations(): List<Location> {
        return withContext(ioDispatcher) {
            locationDataSource.getLocations()
        }
    }

    override suspend fun getLocation(id: String): Location? {
        return withContext(ioDispatcher) {
            locationDataSource.getLocation(id)
        }
    }
}
