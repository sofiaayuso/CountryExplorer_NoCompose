package com.example.countryexplorer.countryexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countryexplorer.database.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for CountryExplorerFragment.
 */

@HiltViewModel
class CountryExplorerViewModel @Inject constructor(
    private val repository: CountryExplorerRepository
): ViewModel() {

    private val _countryExplorerViewStateFlowUpdates: MutableStateFlow<CountryExplorerViewState> = MutableStateFlow(CountryExplorerViewState.NotFound)
    val countryExplorerViewStateFlow: Flow<CountryExplorerViewState> = _countryExplorerViewStateFlowUpdates

    private val _navStateFlow: MutableStateFlow<CountryExplorerNavState?> = MutableStateFlow(null)
    val navStateFlow: Flow<CountryExplorerNavState?> = _navStateFlow

    init {
        _navStateFlow.value = null
        observeCountryData()
    }

    private fun observeCountryData() {
        repository.getCountries()
            .onEach { countries ->
                handleCountryData(countries)
            }.launchIn(viewModelScope)
    }

    private fun handleCountryData(countries: List<Country>) {
        if (countries.isNotEmpty()) {
            _countryExplorerViewStateFlowUpdates.value = CountryExplorerViewState.Loaded(countries)
        } else {
            _countryExplorerViewStateFlowUpdates.value = CountryExplorerViewState.NotFound
        }
    }

    fun onRefreshClicked() {
        viewModelScope.launch {
            _countryExplorerViewStateFlowUpdates.value = CountryExplorerViewState.Loading
            try {
                repository.fetchCountries()
            } catch(e: Exception){
                _countryExplorerViewStateFlowUpdates.value = CountryExplorerViewState.Error
            }
        }
    }

    fun onCountryClicked(countryName: String) {
        _navStateFlow.value = CountryExplorerNavState.NavigateToDetails(countryName)
    }

    fun clearNavState() {
        _navStateFlow.value = null
    }
}