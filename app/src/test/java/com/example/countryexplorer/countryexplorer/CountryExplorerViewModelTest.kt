package com.example.countryexplorer.countryexplorer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.countryexplorer.database.Country
import com.example.countryexplorer.fakes.FakeCountryExplorerRepository
import com.example.countryexplorer.utils.TestCoroutineRule
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountryExplorerViewModelTest {

    val fakeCountryExplorerRepository = FakeCountryExplorerRepository()

    lateinit var viewModel : CountryExplorerViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        fakeCountryExplorerRepository.setCountries(listOf(
            Country("Country1", 1, "flag1", "continent1"),
            Country("Country2", 2, "flag2", "continent2")
        ))
        viewModel = CountryExplorerViewModel(fakeCountryExplorerRepository)
    }

    @Test
    fun `should get country data`() = runTest {
        viewModel.countryExplorerViewStateFlow.test {
            val viewState1: CountryExplorerViewState = awaitItem()
            val viewState2: CountryExplorerViewState = awaitItem()
//            println(viewState1)
//            println(viewState2)
            assertThat(viewState2).isEqualTo(CountryExplorerViewState.Loaded(listOf(
                Country("Country1", 1, "flag1", "continent1"),
                Country("Country2", 2, "flag2", "continent2")
            )))
        }
    }

    @Test
    fun `should return NotFound`() = runTest {
        fakeCountryExplorerRepository.clearCountries()
        viewModel.countryExplorerViewStateFlow.test {
            val viewState1: CountryExplorerViewState = awaitItem()
            assertThat(viewState1).isInstanceOf(CountryExplorerViewState.NotFound.javaClass)
        }
    }
}