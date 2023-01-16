package net.nikonorov.weather.presentation.location

/**
 * Ui Model os the Location Selection Screen
 */
sealed interface LocationSelectionUiState

object LoadingState : LocationSelectionUiState

data class SuccessState(val locations: List<LocationItem>, val selectedItem: Int) :
    LocationSelectionUiState

object ErrorState : LocationSelectionUiState
