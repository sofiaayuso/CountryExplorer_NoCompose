package com.example.countryexplorer.singlecountry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.countryexplorer.R
import com.example.countryexplorer.countryexplorer.CountryExplorerRepository
import com.example.countryexplorer.countryexplorer.CountryExplorerRepositoryImpl
import com.example.countryexplorer.countryexplorer.CountryExplorerViewState
import com.example.countryexplorer.database.CountryDatabase
import com.example.countryexplorer.database.CountryDatabaseDao
import com.example.countryexplorer.databinding.FragmentSingleCountryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_item_country.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import javax.inject.Inject

@AndroidEntryPoint
class SingleCountryFragment() : Fragment() {

    private val viewModel: SingleCountryViewModel by viewModels()
    private lateinit var ui: FragmentSingleCountryBinding
    val args: SingleCountryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        ui = DataBindingUtil.inflate(inflater, R.layout.fragment_single_country, container, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.singleCountryViewStateFlow
                    .collect {
                            singleCountryViewState ->
                        onViewStateUpdate(singleCountryViewState)
                    }
            }
        }

        ui.singleCountryViewModel = viewModel

        return ui.root
    }

    override fun onStart() {
        super.onStart()

        val countryName = getCountryNameFromArgs()
        viewModel.getCountryByName(countryName)
    }


    fun onViewStateUpdate(viewState: SingleCountryViewState) {
        when (viewState) {
            is SingleCountryViewState.NotFound -> {
                ui.flagUrl.isVisible = false
                ui.countryName.isVisible = false
                ui.continent.isVisible = false
                ui.countryPopulation.isVisible = false
                ui.loadingIndicator.isVisible = false
                Toast.makeText(activity, "COUNTRY NOT FOUND", Toast.LENGTH_LONG)
                    .show()
            }
            is SingleCountryViewState.Loading -> {
                ui.flagUrl.isVisible = false
                ui.countryName.isVisible = false
                ui.continent.isVisible = false
                ui.countryPopulation.isVisible = false
                ui.loadingIndicator.isVisible = true
            }
            is SingleCountryViewState.Loaded -> {
                ui.flagUrl.isVisible = true
                ui.countryName.isVisible = true
                ui.continent.isVisible = true
                ui.countryPopulation.isVisible = true
                ui.loadingIndicator.isVisible = false
                ui.countryName.text = viewState.country.name
                ui.countryPopulation.text = resources.getString(R.string.population, viewState.country.population)
                ui.continent.text = viewState.country.continent

            }
            is SingleCountryViewState.Error -> {
                ui.flagUrl.isVisible = false
                ui.countryName.isVisible = false
                ui.continent.isVisible = false
                ui.countryPopulation.isVisible = false
                ui.loadingIndicator.isVisible = false
                Toast.makeText(activity, "Error: Country data not available", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun getCountryNameFromArgs(): String {
        val tv: TextView = ui.countryName
        return arguments?.getString("countryName").toString()
    }

}

