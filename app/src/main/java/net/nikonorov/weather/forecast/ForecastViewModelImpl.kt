package net.nikonorov.weather.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import net.nikonorov.weather.*
import net.nikonorov.weather.presentation.forecast.ForecastUiStateMapper
import net.nikonorov.weather.presentation.forecast.ForecastViewModel
import net.nikonorov.weather.presentation.forecast.ForecastViewModelDelegate
import net.nikonorov.weather.presentation.forecast.WeatherConditionNameMapper

/**
 * Android specific part of ViewModel of the Forecast Screen.
 * The main (domain) functionality is implemented by [ForecastViewModelDelegate]
 */
class ForecastViewModelImpl(delegate: ForecastViewModelDelegate) : ViewModel(),
    ForecastViewModel by delegate {

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>
        ): T {
            val weatherRepository = WeatherRepositoryImpl(
                ioDispatcher = AppDispatchers.IO,
                openMeteoApi = RetrofitHelper.getInstance()
                    .create(OpenMeteoApi::class.java),
                dataMapper = ForecastDataMapper()
            )
            val locationRepository =
                LocationRepositoryImpl(AppDispatchers.IO, LocationDataSourceImpl())
            val uiStateMapper = ForecastUiStateMapper(WeatherConditionNameMapper())
            return ForecastViewModelImpl(
                ForecastViewModelDelegate(weatherRepository, locationRepository, uiStateMapper)
            ) as T
        }
    }
}
