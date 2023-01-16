package net.nikonorov.weather.presentation.location

import net.nikonorov.weather.model.Location

/**
 * Mapper class fpr mapping location data: Domain -> Ui
 */
class LocationItemMapper {
    fun map(location: Location, isSelected: Boolean): LocationItem {
        return LocationItem(location, location.name, isSelected)
    }
}
