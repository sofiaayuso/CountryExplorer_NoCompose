package com.example.countryexplorer.countryexplorer

sealed class CountryExplorerNavState {
    data class NavigateToDetails(val countryName: String) : CountryExplorerNavState()
}