package net.nikonorov.weather.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.MainScope
import net.nikonorov.weather.*
import net.nikonorov.weather.presentation.location.LocationItemMapper
import net.nikonorov.weather.presentation.location.LocationSelectionViewModel
import net.nikonorov.weather.presentation.location.LocationSelectionViewModelDelegate

/**
 * Android specific part of ViewModel of the Location Selection Screen.
 * The main (domain) functionality is implemented by [LocationSelectionViewModelDelegate]
 */
class LocationSelectionViewModelImpl(delegate: LocationSelectionViewModelDelegate) : ViewModel(),
    LocationSelectionViewModel by delegate {

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {
            val repo = LocationRepositoryImpl(AppDispatchers.IO, LocationDataSourceImpl())
            val mapper = LocationItemMapper()
            return LocationSelectionViewModelImpl(
                LocationSelectionViewModelDelegate(MainScope(), repo, mapper)
            ) as T
        }
    }
}
