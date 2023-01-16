package net.nikonorov.weather.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import net.nikonorov.weather.R
import net.nikonorov.weather.presentation.forecast.ForecastErrorState
import net.nikonorov.weather.presentation.forecast.ForecastLoadingState
import net.nikonorov.weather.presentation.forecast.ForecastSuccessState
import net.nikonorov.weather.presentation.forecast.ForecastUiState
import net.nikonorov.weather.util.getString

/**
 * View layer of the Forecast Screen
 */
class ForecastFragment : Fragment() {

    private val viewModel: ForecastViewModelImpl by viewModels { ForecastViewModelImpl.Factory() }
    private lateinit var progressBar: View
    private lateinit var successStateViews: Group
    private lateinit var retryButton: View
    private lateinit var errorStateViews: Group
    private lateinit var locationNameView: TextView
    private lateinit var currentTemperatureView: TextView
    private lateinit var weatherConditionView: TextView
    private lateinit var temperatureRangeView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    renderState(it)
                }
            }
        }
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
        successStateViews = view.findViewById(R.id.success_state_group)
        locationNameView = view.findViewById(R.id.location_name_view)
        retryButton = view.findViewById(R.id.retry_button)
        retryButton.setOnClickListener { fetchData() }
        errorStateViews = view.findViewById(R.id.error_group)
        currentTemperatureView = view.findViewById(R.id.current_temperature)
        weatherConditionView = view.findViewById(R.id.weather_condition)
        temperatureRangeView = view.findViewById(R.id.temperature_range)
    }

    private fun renderState(state: ForecastUiState) {
        when (state) {
            ForecastLoadingState -> {
                progressBar.visibility = View.VISIBLE
                successStateViews.visibility = View.GONE
                errorStateViews.visibility = View.GONE
            }
            is ForecastSuccessState -> {
                progressBar.visibility = View.GONE
                errorStateViews.visibility = View.GONE
                successStateViews.visibility = View.VISIBLE
                locationNameView.text = state.locationName
                currentTemperatureView.text = getString(state.currentTemperature)
                state.weatherCondition?.let { weatherConditionView.text = getString(it) }
                    ?: run { weatherConditionView.visibility = View.GONE }
                temperatureRangeView.text = getString(state.temperatureRange)
            }
            is ForecastErrorState -> {
                errorStateViews.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                successStateViews.visibility = View.GONE
            }
        }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            val locationId = requireArguments().getString(LOCATION_ID_ARG_KEY)
            locationId?.let {
                viewModel.fetchData(it)
            } ?: throw IllegalArgumentException("Missing location id")
        }
    }

    companion object {
        private const val LOCATION_ID_ARG_KEY = "location_id_key"
        fun newInstance(locationId: String): Fragment {
            return ForecastFragment().also {
                it.arguments = bundleOf(
                    LOCATION_ID_ARG_KEY to locationId
                )
            }
        }
    }
}
