package com.example.countryexplorer.countryexplorer

import com.example.countryexplorer.database.Country
import com.example.countryexplorer.database.CountryDatabaseDao
import com.example.countryexplorer.network.CountriesApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMap
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
//        println(countryName)
//        dao.getCountries().collect {
//            it.onEach {
//                println(it.name)
//                println(it.name == countryName)
//            }
//        }
        return kotlin.runCatching {
            println(countryName)
            println(dao.getCountryByName(countryName))
            dao.getCountryByName(countryName)
        }
    }
}