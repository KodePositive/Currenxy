package com.geneus.currenxy.domain.viewmodel

import app.cash.turbine.test
import com.geneus.currenxy.domain.model.CurrencyInfo
import com.geneus.currenxy.domain.usecase.ClearCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetAllCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetCryptoCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetFiatCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.InsertCurrenciesUseCase
import com.geneus.currenxy.ui.fragments.currencylist.CurrencyListType
import com.geneus.currenxy.ui.fragments.currencylist.CurrencyListViewModel
import com.geneus.currenxy.util.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyListViewModelTest {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val insertCurrenciesUseCase = mockk<InsertCurrenciesUseCase>(relaxed = true)
    private val clearCurrenciesUseCase = mockk<ClearCurrenciesUseCase>(relaxed = true)
    private val getCryptoCurrenciesUseCase = mockk<GetCryptoCurrenciesUseCase>()
    private val getFiatCurrenciesUseCase = mockk<GetFiatCurrenciesUseCase>()
    private val getAllCurrenciesUseCase = mockk<GetAllCurrenciesUseCase>()

    private lateinit var viewModel: CurrencyListViewModel

    private val mockCryptoCurrencies = listOf(
        CurrencyInfo(name = "Bitcoin", symbol = "BTC", code = "BTC", id = "1"),
        CurrencyInfo(name = "Ethereum", symbol = "ETH", code = "ETH", id = "2")
    )

    private val mockFiatCurrencies = listOf(
        CurrencyInfo(name = "US Dollar", symbol = "USD", code = "USD", id = "1"),
        CurrencyInfo(name = "Euro", symbol = "EUR", code = "EUR", id = "2")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Override Main dispatcher

        coEvery { getCryptoCurrenciesUseCase.execute(Unit) } returns flowOf(mockCryptoCurrencies)
        coEvery { getFiatCurrenciesUseCase.execute(Unit) } returns flowOf(mockFiatCurrencies)
        coEvery { getAllCurrenciesUseCase.execute(Unit) } returns flowOf(mockCryptoCurrencies + mockFiatCurrencies)

        viewModel = CurrencyListViewModel(
            insertCurrenciesUseCase,
            clearCurrenciesUseCase,
            getCryptoCurrenciesUseCase,
            getFiatCurrenciesUseCase,
            getAllCurrenciesUseCase
        )
    }

    @Test
    fun `initial state emits loading`() = testScope.runTest {
        viewModel.uiState.test {
            assertEquals(UiState.loading(null), awaitItem())
        }
    }

    @Test
    fun `fetch crypto currencies updates UI state to success`() = testScope.runTest {
        viewModel.setType(CurrencyListType.CRYPTO)
        viewModel.uiState.test {
            awaitItem() 
            assertEquals(UiState.success(mockCryptoCurrencies), awaitItem())
        }
    }

    @Test
    fun `fetch fiat currencies updates UI state to success`() = testScope.runTest {
        viewModel.setType(CurrencyListType.FIAT)
        viewModel.uiState.test {
            awaitItem()
            assertEquals(UiState.success(mockFiatCurrencies), awaitItem())
        }
    }

    @Test
    fun `filter currencies by query updates UI state`() = testScope.runTest {
        viewModel.setType(CurrencyListType.ALL)
        viewModel.setQuery("USD")
        viewModel.uiState.test {
            awaitItem()
            val filteredList = mockFiatCurrencies.filter { it.code?.startsWith("USD", true) == true }
            assertEquals(UiState.success(filteredList), awaitItem())
        }

        viewModel.setQuery("BTC")
        viewModel.uiState.test {
            awaitItem()
            val filteredList = mockCryptoCurrencies.filter { it.symbol.startsWith("BTC", true) }
            assertEquals(UiState.success(filteredList), awaitItem())
        }

        viewModel.setQuery("invalid query")
        viewModel.uiState.test {
            awaitItem()

            val emptyList = emptyList<List<CurrencyInfo>>()
            assertEquals(UiState.success(emptyList), awaitItem())
        }
    }
}
