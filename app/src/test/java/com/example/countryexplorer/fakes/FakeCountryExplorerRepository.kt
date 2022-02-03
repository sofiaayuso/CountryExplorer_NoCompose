package com.example.countryexplorer.fakes

import com.example.countryexplorer.database.Country
import com.example.countryexplorer.network.CountryExplorerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCountryExplorerRepository : CountryExplorerRepository {

    private val countryList = mutableListOf<Country>()

    override suspend fun fetchCountries() {
        TODO("Not yet implemented")
    }

    override fun getCountries(): Flow<List<Country>> {
        return flowOf(countryList)
    }

    override suspend fun getCountryByName(countryName: String): Result<Country> {
        return Result.success(Country("Country1", 1, "flag1", "continent1"))
    }

    fun setCountries(countries: List<Country>) {
        countryList.clear()
        countryList.addAll(countries)
    }

    fun clearCountries() {
        countryList.clear()
    }
}