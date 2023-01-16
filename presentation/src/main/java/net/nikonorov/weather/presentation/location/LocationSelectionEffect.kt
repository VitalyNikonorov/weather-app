package net.nikonorov.weather.presentation.location

import net.nikonorov.weather.resources.StringWrapper

/**
 * One-time events used by Location Selection Screen
 */
sealed interface LocationSelectionEffect

data class OpenForecastScreen(val locationId: String) : LocationSelectionEffect

data class ShowToast(val message: StringWrapper) : LocationSelectionEffect
