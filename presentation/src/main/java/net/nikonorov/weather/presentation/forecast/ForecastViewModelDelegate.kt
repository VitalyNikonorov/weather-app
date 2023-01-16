package net.nikonorov.weather.presentation.forecast

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.nikonorov.weather.data.LocationRepository
import net.nikonorov.weather.data.WeatherRepository

/**
 * Domain part of the View model of the Forecast Screen
 */
class ForecastViewModelDelegate(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val uiStateMapper: ForecastUiStateMapper,
) : ForecastViewModel {

    private val _uiState: MutableStateFlow<ForecastUiState> = MutableStateFlow(ForecastLoadingState)
    override val uiState: Flow<ForecastUiState> = _uiState.asStateFlow()

    override suspend fun fetchData(locationId: String) {
        _uiState.value = ForecastLoadingState
        locationRepository.getLocation(locationId)?.let { location ->
            weatherRepository.fetchWeatherData(location).onSuccess {
                _uiState.value = uiStateMapper.map(it, location.name)
            }.onFailure {
                _uiState.value = ForecastErrorState
            }
        } ?: run {
            _uiState.value = ForecastErrorState
        }
    }
}
