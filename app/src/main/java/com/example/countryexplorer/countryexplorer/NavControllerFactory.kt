package com.example.countryexplorer.countryexplorer

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.countryexplorer.R
import javax.inject.Inject


class NavControllerFactory @Inject constructor(){

    fun providesNavController(activity: Activity) : NavController{
        return activity.findNavController(R.id.countryExplorerFragment)
    }
}