package net.nikonorov.weather.presentation.location

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.nikonorov.weather.data.LocationRepository
import net.nikonorov.weather.model.Coordinate
import net.nikonorov.weather.model.Location
import net.nikonorov.weather.resources.StringWrapper
import net.nikonorov.weather.resources.Strings
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LocationSelectionViewModelDelegateTest {

    private val testScope = TestScope()
    private val locationRepository: LocationRepository = mock()
    private val locationItemMapper: LocationItemMapper = mock()

    private val delegate: LocationSelectionViewModelDelegate =
        LocationSelectionViewModelDelegate(testScope, locationRepository, locationItemMapper)

    @Test
    fun `initial success state with no selection`() = testScope.runTest {
        // Arrange
        val testLocation = Location(id = "0", "Test City", Coordinate(30.0, 45.0), "Test/Zone")
        val testItem = LocationItem(testLocation, "Test City", false)
        whenever(locationRepository.getLocations()).thenReturn(listOf(testLocation))
        whenever(locationItemMapper.map(testLocation, isSelected = false)).thenReturn(testItem)
        var uiState: LocationSelectionUiState? = null

        // Act
        val observeUiStateJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            delegate.uiState.collect {
                uiState = it
            }
        }
        delegate.fetchData()

        // Assert
        assertThat(uiState).isNotNull
        assertThat(uiState).isInstanceOf(SuccessState::class.java)
        with(uiState as SuccessState) {
            assertThat(selectedItem).isEqualTo(-1)
            assertThat(locations).isEqualTo(listOf(testItem))
        }

        observeUiStateJob.cancel()
    }

    @Test
    fun `location selection success flow`() = testScope.runTest {
        // Arrange
        val testLocation1 = Location(id = "1", "Test City 1", Coordinate(30.0, 45.0), "Test/Zone1")
        val testLocation2 = Location(id = "2", "Test City 2", Coordinate(45.0, 30.0), "Test/Zone2")
        val testItem1 = LocationItem(testLocation1, "Test City 1", false)
        val testItem2 = LocationItem(testLocation2, "Test City 2", false)
        val testItem1Selected = LocationItem(testLocation1, "Test City 1", true)
        val testItem2Selected = LocationItem(testLocation2, "Test City 2", true)

        whenever(locationRepository.getLocations()).thenReturn(listOf(testLocation1, testLocation2))
        whenever(locationItemMapper.map(testLocation1, isSelected = false)).thenReturn(testItem1)
        whenever(locationItemMapper.map(testLocation2, isSelected = false)).thenReturn(testItem2)
        whenever(locationItemMapper.map(testLocation2, isSelected = true)).thenReturn(
            testItem2Selected
        )
        whenever(locationItemMapper.map(testLocation1, isSelected = true)).thenReturn(
            testItem1Selected
        )
        var uiState: LocationSelectionUiState? = null

        // Act
        val observeUiStateJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            delegate.uiState.collect {
                uiState = it
            }
        }
        delegate.fetchData()
        delegate.onSelectionActionButtonClick(testLocation2)

        // Assert
        assertThat(uiState).isNotNull
        assertThat(uiState).isInstanceOf(SuccessState::class.java)
        with(uiState as SuccessState) {
            assertThat(selectedItem).isEqualTo(1)
            assertThat(locations).isEqualTo(listOf(testItem1, testItem2Selected))
        }

        // Act
        delegate.onSelectionActionButtonClick(testLocation1)

        // Assert
        assertThat(uiState).isNotNull
        assertThat(uiState).isInstanceOf(SuccessState::class.java)
        with(uiState as SuccessState) {
            assertThat(selectedItem).isEqualTo(0)
            assertThat(locations).isEqualTo(listOf(testItem1Selected, testItem2))
        }

        observeUiStateJob.cancel()
    }

    @Test
    fun `location selection proceed flow`() = testScope.runTest {
        // Arrange
        val testLocation1 = Location(id = "1", "Test City", Coordinate(45.0, 30.0), "Test/Zone1")
        val testItem1 = LocationItem(testLocation1, "Test City", false)
        val testItem1Selected = LocationItem(testLocation1, "Test City", true)

        whenever(locationRepository.getLocations()).thenReturn(listOf(testLocation1))
        whenever(locationItemMapper.map(testLocation1, isSelected = false)).thenReturn(testItem1)
        whenever(locationItemMapper.map(testLocation1, isSelected = true)).thenReturn(
            testItem1Selected
        )
        var effect: LocationSelectionEffect? = null

        // Act
        val observeUiEffectsJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            delegate.effects.collect {
                effect = it
            }
        }
        delegate.fetchData()
        delegate.onNextClick()
        advanceUntilIdle()

        // Assert
        assertThat(effect).isNotNull
        assertThat(effect).isInstanceOf(ShowToast::class.java)
        with(effect as ShowToast) {
            assertThat(message).isEqualTo(StringWrapper.from(Strings.SelectLocationMessage))
        }

        // Act
        delegate.onSelectionActionButtonClick(testLocation1)
        delegate.onNextClick()
        advanceUntilIdle()

        // Assert
        assertThat(effect).isNotNull
        assertThat(effect).isInstanceOf(OpenForecastScreen::class.java)
        with(effect as OpenForecastScreen) {
            assertThat(locationId).isEqualTo("1")
        }

        observeUiEffectsJob.cancel()
    }
}
