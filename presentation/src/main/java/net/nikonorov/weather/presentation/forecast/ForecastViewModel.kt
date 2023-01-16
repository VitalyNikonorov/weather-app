package net.nikonorov.weather.presentation.forecast

import net.nikonorov.weather.presentation.base.BaseViewModel

interface ForecastViewModel : BaseViewModel<ForecastUiState> {
    suspend fun fetchData(locationId: String)
}
