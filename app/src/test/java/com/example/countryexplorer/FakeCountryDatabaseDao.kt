package com.example.countryexplorer

import com.example.countryexplorer.database.Country
import com.example.countryexplorer.database.CountryDatabaseDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.assertj.core.api.AssertionsForClassTypes.assertThat

class FakeCountryDatabaseDao: CountryDatabaseDao {

    val countriesArray = mutableListOf<Country>()

    override suspend fun upsertMany(countries: List<Country>) {
        countriesArray.addAll(countries)
    }

    override fun getCountries(): Flow<List<Country>> {
        return flowOf(countriesArray)
    }

    override suspend fun deleteCountries() {
        countriesArray.clear()
    }

    override suspend fun getCountryByName(countryName: String): Country {
        return countriesArray.first { it.name == countryName }
    }

    fun assertCountries(countriesList: List<Country>) {
        assertThat(countriesArray).isEqualTo(countriesList)
    }

}