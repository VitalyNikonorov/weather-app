package net.nikonorov.weather

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.nikonorov.weather.data.LocationRepository
import net.nikonorov.weather.data.WeatherRepository
import net.nikonorov.weather.presentation.forecast.ForecastSuccessState
import net.nikonorov.weather.presentation.forecast.ForecastUiState
import net.nikonorov.weather.presentation.forecast.ForecastUiStateMapper
import net.nikonorov.weather.presentation.forecast.ForecastViewModelDelegate
import net.nikonorov.weather.presentation.forecast.WeatherConditionNameMapper
import net.nikonorov.weather.resources.StringWrapper
import org.assertj.core.api.Assertions
import org.junit.Test
import java.time.LocalDateTime
import java.time.Month
import java.time.ZonedDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class ForecastFit {
    private val testScope = TestScope()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val uiStateMapper = ForecastUiStateMapper(
        conditionNameMapper = WeatherConditionNameMapper(),
        nowDateProvider = { ZonedDateTime.of(LocalDateTime.of(2023, Month.JUNE, 21, 15, 0), it) },
    )
    private val dataMapper: ForecastDataMapper = ForecastDataMapper()
    private val openMeteoApi = FileBasedApiClient()
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(testDispatcher, openMeteoApi, dataMapper)
    private val locationRepository: LocationRepository =
        LocationRepositoryImpl(testDispatcher, LocationDataSourceTestImpl())
    private val delegate: ForecastViewModelDelegate =
        ForecastViewModelDelegate(weatherRepository, locationRepository, uiStateMapper)

    @Test
    fun `test success forecast`() = testScope.runTest {
        // Arrange
        openMeteoApi.setupFilePath(TestConst.simpleForecastResponse)
        var uiState: ForecastUiState? = null

        // Act
        val observeUiStateJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            delegate.uiState.collect {
                uiState = it
            }
        }
        delegate.fetchData(TestConst.testLocation1.id)
        advanceUntilIdle()

        // Assert
        Assertions.assertThat(uiState).isNotNull
        Assertions.assertThat(uiState).isInstanceOf(ForecastSuccessState::class.java)
        with(uiState as ForecastSuccessState) {
            Assertions.assertThat(locationName).isEqualTo(TestConst.testLocation1.name)
            Assertions.assertThat(currentTemperature).isInstanceOf(StringWrapper.StringResourceWrapper::class.java)
            val temp = currentTemperature as StringWrapper.StringResourceWrapper
            Assertions.assertThat(temp.args).isEqualTo(listOf(14, "°C"))
        }

        observeUiStateJob.cancel()
    }
}
