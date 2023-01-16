package net.nikonorov.weather

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.nikonorov.weather.data.LocationRepository
import net.nikonorov.weather.presentation.location.*
import org.assertj.core.api.Assertions
import org.junit.Test

@ExperimentalCoroutinesApi
class LocationSelectionFit {
    private val testScope = TestScope()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val locationItemMapper = LocationItemMapper()
    private val locationRepository: LocationRepository =
        LocationRepositoryImpl(testDispatcher, LocationDataSourceTestImpl())
    private val delegate: LocationSelectionViewModel =
        LocationSelectionViewModelDelegate(testScope, locationRepository, locationItemMapper)

    @Test
    fun `test location selection flow`() = testScope.runTest {
        // Arrange
        var uiState: LocationSelectionUiState? = null

        // Act
        val observeUiStateJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            delegate.uiState.collect {
                uiState = it
            }
        }
        delegate.fetchData()
        delegate.onSelectionActionButtonClick(TestConst.testLocation1)
        advanceUntilIdle()

        // Assert
        Assertions.assertThat(uiState).isNotNull
        Assertions.assertThat(uiState).isInstanceOf(SuccessState::class.java)
        with(uiState as SuccessState) {
            Assertions.assertThat(selectedItem).isEqualTo(0)
            Assertions.assertThat(locations.map { it.location })
                .isEqualTo(listOf(TestConst.testLocation1, TestConst.testLocation2))
        }

        // Act
        delegate.onSelectionActionButtonClick(TestConst.testLocation2)
        advanceUntilIdle()

        // Assert
        Assertions.assertThat(uiState).isNotNull
        Assertions.assertThat(uiState).isInstanceOf(SuccessState::class.java)
        with(uiState as SuccessState) {
            Assertions.assertThat(selectedItem).isEqualTo(1)
            Assertions.assertThat(locations.map { it.location })
                .isEqualTo(listOf(TestConst.testLocation1, TestConst.testLocation2))
        }

        observeUiStateJob.cancel()
    }
}
