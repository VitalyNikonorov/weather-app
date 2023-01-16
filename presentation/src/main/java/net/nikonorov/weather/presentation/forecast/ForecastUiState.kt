package net.nikonorov.weather.presentation.forecast

import net.nikonorov.weather.resources.StringWrapper

/**
 * Ui State of the forecast screen
 */
sealed interface ForecastUiState

object ForecastLoadingState : ForecastUiState

object ForecastErrorState : ForecastUiState

data class ForecastSuccessState(
    val locationName: String,
    val weatherCondition: StringWrapper?,
    val currentTemperature: StringWrapper,
    val temperatureRange: StringWrapper,
) : ForecastUiState
