package net.nikonorov.weather.location

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import net.nikonorov.weather.MainActivity
import net.nikonorov.weather.R
import net.nikonorov.weather.presentation.location.*
import net.nikonorov.weather.resources.StringWrapper
import net.nikonorov.weather.util.getString

/**
 * View layer of the Location Selection Screen
 */
class LocationSelectionFragment : Fragment() {

    private val viewModel: LocationSelectionViewModelImpl by viewModels { LocationSelectionViewModelImpl.Factory() }
    private lateinit var progressBar: View
    private lateinit var retryButton: View
    private lateinit var errorViews: Group
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocationsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    renderState(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effects.collect {
                    handleEffect(it)
                }
            }
        }
        fetchData()
        adapter =
            LocationsListAdapter { item -> viewModel.onSelectionActionButtonClick(item.location) }
    }

    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.fetchData()
        }
    }

    private fun renderState(state: LocationSelectionUiState) {
        when (state) {
            LoadingState -> {
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                errorViews.visibility = View.GONE
                adapter.setData(emptyList())
            }
            is SuccessState -> {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                errorViews.visibility = View.GONE
                adapter.setData(state.locations)
            }
            ErrorState -> {
                errorViews.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
                adapter.setData(emptyList())
            }
        }
    }

    private fun handleEffect(effect: LocationSelectionEffect) {
        when (effect) {
            is OpenForecastScreen -> {
                (requireActivity() as MainActivity).openForecastScreen(effect.locationId)
            }
            is ShowToast -> showsToast(effect.message)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_selection_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
        retryButton = view.findViewById(R.id.retry_button)
        retryButton.setOnClickListener { fetchData() }
        errorViews = view.findViewById(R.id.error_group)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        setupMenu()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.add(0, 0, 0, R.string.next_action_bar_button)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    0 -> {
                        viewModel.onNextClick()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showsToast(message: StringWrapper) {
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_LONG).show()
    }
}
