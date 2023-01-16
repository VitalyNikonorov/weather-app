package net.nikonorov.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.nikonorov.weather.forecast.ForecastFragment
import net.nikonorov.weather.location.LocationSelectionFragment

/**
 * The main activity of the application.
 * Hosts all the fragments of the main flow
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportFragmentManager.beginTransaction()
            .add(R.id.content_view, LocationSelectionFragment()).commit()
    }

    fun openForecastScreen(locationId: String) {
        val fragment = ForecastFragment.newInstance(locationId)
        supportFragmentManager.beginTransaction().addToBackStack(null)
            .replace(R.id.content_view, fragment).commit()
    }
}
