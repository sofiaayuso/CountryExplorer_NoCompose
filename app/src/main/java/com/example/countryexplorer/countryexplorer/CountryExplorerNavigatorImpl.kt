package com.example.countryexplorer.countryexplorer

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.countryexplorer.MainActivity
import com.example.countryexplorer.R
import javax.inject.Inject

class CountryExplorerNavigatorImpl @Inject constructor(
    private val activity: MainActivity,
    private val navControllerFactory: NavControllerFactory
): CountryExplorerNavigator  {

    override fun navigateToDetails(countryName: String) {
        val bundle = bundleOf("countryName" to countryName)
        navControllerFactory.providesNavController(activity)
            .navigate(R.id.action_countryExplorerFragment_to_singleCountryFragment, bundle)
    }

}