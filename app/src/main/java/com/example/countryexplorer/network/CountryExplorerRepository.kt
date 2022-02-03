package com.example.countryexplorer.network

import com.example.countryexplorer.database.Country
import com.example.countryexplorer.database.CountryDatabaseDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CountryExplorerRepository {

    suspend fun fetchCountries()

    fun getCountries(): Flow<List<Country>>

    suspend fun getCountryByName(countryName: String): Result<Country>
}

class CountryExplorerRepositoryImpl @Inject constructor (
    private val dao: CountryDatabaseDao,
    private val countryExplorerService: CountriesApiService
    ) : CountryExplorerRepository {

    override suspend fun fetchCountries() {

        val countries = countryExplorerService.getCountries().map { it.toCountry() }
        delay( 1000 )
        dao.upsertMany(countries)
    }

    override fun getCountries(): Flow<List<Country>> {
        return dao.getCountries()
    }

    override suspend fun getCountryByName(countryName: String): Result<Country> {
        return kotlin.runCatching {  dao.getCountryByName(countryName) }
    }
}