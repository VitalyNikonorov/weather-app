package net.nikonorov.weather

import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.Month
import java.time.format.DateTimeParseException

class TimeDataMapperTest {
    private val mapper: TimeDataMapper = TimeDataMapper()

    @Test
    fun `map valid input with few items correctly`() {
        // Arrange
        val dateTimeItems = listOf("2022-12-31T23:59", "2023-01-01T00:00")
        val inputTimezone = "Europe/London"

        // Act
        val mapped = mapper.map(dateTimeItems, inputTimezone)

        // Assert
        assertThat(mapped!!.size).isEqualTo(2)
        with(mapped[0]) {
            assertThat(year).isEqualTo(2022)
            assertThat(month).isEqualTo(Month.DECEMBER)
            assertThat(dayOfMonth).isEqualTo(31)
            assertThat(hour).isEqualTo(23)
            assertThat(minute).isEqualTo(59)
            assertThat(zone.id).isEqualTo("Europe/London")
        }
        with(mapped[1]) {
            assertThat(year).isEqualTo(2023)
            assertThat(month).isEqualTo(Month.JANUARY)
            assertThat(dayOfMonth).isEqualTo(1)
            assertThat(hour).isEqualTo(0)
            assertThat(minute).isEqualTo(0)
            assertThat(zone.id).isEqualTo("Europe/London")
        }
    }

    @Test
    fun `map missing null list to null`() {
        // Arrange
        val dateTimeItems: List<String?>? = null
        val timezone = "Europe/London"

        // Act
        val mapped = mapper.map(dateTimeItems, timezone)

        // Assert
        assertThat(mapped).isNull()
    }

    @Test
    fun `map valid items with missing timezone to null`() {
        // Arrange
        val dateTimeItems = listOf("2023-01-01T00:00")
        val timezone = null

        // Act
        val mapped = mapper.map(dateTimeItems, timezone)

        // Assert
        assertNull(mapped)
    }

    @Test
    fun `map list of items with null item to null`() {
        // Arrange
        val dateTimeItems = listOf("2023-01-01T00:00", null)
        val timezone = "Europe/London"

        // Act
        val mapped = mapper.map(dateTimeItems, timezone)

        // Assert
        assertThat(mapped).isNull()
    }

    @Test(expected = DateTimeParseException::class)
    fun `map incorrect datetime format throws an exception`() {
        // Arrange
        val dateTimeWithMissingDivider = "2023-01-0100:00"
        val dateTimeItems = listOf(dateTimeWithMissingDivider)
        val timezone = "Europe/London"

        // Act
        mapper.map(dateTimeItems, timezone)
    }
}
