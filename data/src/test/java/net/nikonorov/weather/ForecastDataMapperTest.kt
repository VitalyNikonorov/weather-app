package net.nikonorov.weather

import org.assertj.core.api.Assertions
import org.junit.Test

class ForecastDataMapperTest {

    @Test
    fun `map correct input with 2 items to correct output with 2 items`() {
        // Arrange
        val mapper = ForecastDataMapper()
        val correctForecastWith2Items = buildDefaultForecastApiResponse()

        // Act
        val mapped = mapper.map(correctForecastWith2Items)

        // Assert
        Assertions.assertThat(mapped).isNotNull
        // Some mandatory checks
        Assertions.assertThat(mapped!!.temperature.data.size).isEqualTo(2)
    }

    private fun buildDefaultForecastApiResponse(
        lat: Double? = -33.87,
        lon: Double? = 151.21,
        generationTimeMillis: Double? = 0.55,
        utcOffsetSeconds: Int? = 39600,
        timezone: String? = "Australia/Sydney",
        timezoneAbbreviation: String? = "AEDT",
        elevation: Double? = 658.0,
        hourlyUnits: HourlyDataUnitsApiResponse? = buildDefaultHourlyUnits(),
        hourlyData: HourlyDataApiResponse? = buildDefaultHourlyDataApiResponse(),
    ): ForecastApiResponse {
        return ForecastApiResponse().apply {
            this.lat = lat
            this.lon = lon
            this.generationTimeMillis = generationTimeMillis
            this.utcOffsetSeconds = utcOffsetSeconds
            this.timezone = timezone
            this.timezoneAbbreviation = timezoneAbbreviation
            this.elevation = elevation
            this.hourlyUnits = hourlyUnits
            this.hourlyData = hourlyData
        }
    }

    private fun buildDefaultHourlyUnits(
        time: String? = "iso8601",
        temperature: String? = "°C",
        humidity: String? = "%",
        precipitation: String? = "mm",
        windSpeed: String? = "km/h",
        weatherCode: String? = "wmo code",
    ): HourlyDataUnitsApiResponse {
        return HourlyDataUnitsApiResponse().apply {
            this.time = time
            this.temperature = temperature
            this.humidity = humidity
            this.precipitation = precipitation
            this.windSpeed = windSpeed
            this.weatherCode = weatherCode
        }
    }

    private fun buildDefaultHourlyDataApiResponse(
        time: List<String?>? = listOf("2023-01-22T00:00", "2023-01-22T01:00"),
        temperature: List<Double?>? = listOf(14.4, 14.2),
        humidity: List<Int?>? = listOf(86, 87),
        precipitation: List<Double?>? = listOf(0.0, 1.4),
        windSpeed: List<Double?>? = listOf(3.1, 2.2),
        weatherCode: List<Int?>? = listOf(3, 80),
    ): HourlyDataApiResponse {
        return HourlyDataApiResponse().apply {
            this.time = time
            this.temperature = temperature
            this.humidity = humidity
            this.precipitation = precipitation
            this.windSpeed = windSpeed
            this.weatherCode = weatherCode
        }
    }
}