package com.example.countryexplorer.singlecountry

import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countryexplorer.countryexplorer.CountryExplorerRepository
import com.example.countryexplorer.countryexplorer.CountryExplorerRepositoryImpl
import com.example.countryexplorer.database.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleCountryViewModel @Inject constructor(

    private val repository: CountryExplorerRepository): ViewModel() {

    private val _singleCountryViewStateFlowUpdates: MutableStateFlow<SingleCountryViewState> = MutableStateFlow(
        SingleCountryViewState.Loading)
    val singleCountryViewStateFlow: Flow<SingleCountryViewState> = _singleCountryViewStateFlowUpdates

    fun getCountryByName (countryName: String) {
        viewModelScope.launch {

            val result = repository.getCountryByName(countryName)
            result.fold({
                _singleCountryViewStateFlowUpdates.value = SingleCountryViewState.Loaded(it)
            }){
                _singleCountryViewStateFlowUpdates.value = SingleCountryViewState.Error
            }

        }
    }

}

