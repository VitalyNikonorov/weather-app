package net.nikonorov.weather.presentation.location

import net.nikonorov.weather.model.Location

/**
 * Ui Model os the items used by Location Selection Screen
 */
data class LocationItem(
    val location: Location,
    val itemLabel: String,
    val isSelected: Boolean
)
