package net.nikonorov.weather.presentation.location

import kotlinx.coroutines.flow.Flow
import net.nikonorov.weather.model.Location
import net.nikonorov.weather.presentation.base.BaseViewModel

interface LocationSelectionViewModel : BaseViewModel<LocationSelectionUiState> {
    val effects: Flow<LocationSelectionEffect>
    suspend fun fetchData()
    fun onNextClick()
    fun onSelectionActionButtonClick(item: Location)
}
