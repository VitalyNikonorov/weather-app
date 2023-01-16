package net.nikonorov.weather.presentation.location

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.nikonorov.weather.data.LocationRepository
import net.nikonorov.weather.model.LoadableData
import net.nikonorov.weather.model.Location
import net.nikonorov.weather.resources.StringWrapper
import net.nikonorov.weather.resources.Strings

/**
 * Domain part of the ViewModel of Location Selection Screen
 */
class LocationSelectionViewModelDelegate(
    private val mainScope: CoroutineScope,
    private val repo: LocationRepository,
    private val locationItemMapper: LocationItemMapper
) : LocationSelectionViewModel {

    private val _effects = MutableSharedFlow<LocationSelectionEffect>()
    override val effects: Flow<LocationSelectionEffect> = _effects.asSharedFlow()
    private val selectedItem = MutableStateFlow<Location?>(null)
    private val locationList =
        MutableStateFlow<LoadableData<List<Location>, Unit>>(LoadableData.Loading(null))

    override val uiState: Flow<LocationSelectionUiState> =
        combine(selectedItem, locationList) { selectedLocation, locations ->
            when (locations) {
                is LoadableData.Error -> ErrorState
                is LoadableData.Loading -> LoadingState
                is LoadableData.Success -> buildSuccessState(locations.data, selectedLocation)
            }
        }

    override suspend fun fetchData() {
        locationList.value = LoadableData.Loading(locationList.value.data)
        locationList.value = LoadableData.Success(repo.getLocations())
    }

    override fun onNextClick() {
        mainScope.launch {
            selectedItem.value?.let {
                _effects.emit(OpenForecastScreen(it.id))
            } ?: run {
                _effects.emit(ShowToast(StringWrapper.from(Strings.SelectLocationMessage)))
            }
        }
    }

    override fun onSelectionActionButtonClick(item: Location) {
        selectedItem.value = item
    }

    private fun buildSuccessState(
        locations: List<Location>, selectedLocation: Location?
    ): SuccessState {
        val items = locations.map { item ->
            locationItemMapper.map(
                location = item, isSelected = selectedItem.value == item
            )
        }
        val selectedItemIndex: Int = items.indexOfFirst { it.location.id == selectedLocation?.id }
        if (selectedItemIndex == -1 && selectedItem.value != null) {
            selectedItem.value = null
        }
        return SuccessState(items, selectedItemIndex)
    }
}
