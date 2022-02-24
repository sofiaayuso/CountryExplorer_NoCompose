package com.example.countryexplorer

import com.example.countryexplorer.countryexplorer.CountryExplorerRepositoryImpl
import com.example.countryexplorer.database.Country
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CountryExplorerRepositoryImplTest {

    val fakeDao = FakeCountryDatabaseDao()
    val fakeCountryExplorerService = FakeCountriesApiService()
    val repository = CountryExplorerRepositoryImpl(fakeDao, fakeCountryExplorerService)

    @Test
    fun `can fetch countries from api`() = runTest {
        repository.fetchCountries()
        val country = Country("commonName", 10, "pngString", "Continent1")
        val countriesArray = listOf(country)
        fakeDao.assertCountries(countriesArray)
    }

    @Test
    fun `countries can be retrieved from the dao`() = runTest {
        val country = Country("commonName", 10, "pngString", "Continent1")
        val countriesArray = listOf(country)
        fakeDao.upsertMany(countriesArray)
        assertThat(repository.getCountries().single()).isEqualTo(countriesArray)
    }

    @Test
    fun `country can be found by name`() = runTest {
        val country = Country("commonName", 10, "pngString", "Continent1")
        val countriesArray = listOf(country)
        fakeDao.upsertMany(countriesArray)
        val result = repository.getCountryByName("commonName")
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `country can not be found by name`() = runTest {
        val result = repository.getCountryByName("countryNameNotInList")
        assertThat(result.isFailure).isTrue()
    }
}