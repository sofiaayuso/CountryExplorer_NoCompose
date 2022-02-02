package com.example.countryexplorer.singlecountry

import com.example.countryexplorer.database.Country

sealed class SingleCountryViewState {
    data class Loaded(val country: Country): SingleCountryViewState()
    object Loading : SingleCountryViewState()
    object NotFound : SingleCountryViewState()
    object  Error: SingleCountryViewState()
}