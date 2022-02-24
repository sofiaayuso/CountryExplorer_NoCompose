package com.example.countryexplorer

import com.example.countryexplorer.database.RemoteCountry
import com.example.countryexplorer.database.RemoteFlags
import com.example.countryexplorer.database.RemoteName
import com.example.countryexplorer.network.CountriesApiService

class FakeCountriesApiService: CountriesApiService {

    override suspend fun getCountries(): List<RemoteCountry> {
        val country1 = RemoteCountry(RemoteName("commonName", "officialName"), RemoteFlags("pngString", "svgString"), 10, "Continent1")
        val countryList = listOf(country1)
        return countryList
    }

}