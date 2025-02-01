package com.geneus.currenxy.domain.usecase

import com.geneus.domain.model.CurrencyInfo
import com.geneus.domain.repository.CurrencyRepository
import com.geneus.domain.usecase.GetAllCurrenciesUseCase
import com.geneus.domain.usecase.GetCryptoCurrenciesUseCase
import com.geneus.domain.usecase.GetFiatCurrenciesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetCurrenciesUseCaseTests {
    @Test
    fun `verify getCryptoCurrencies returns cryptocurrency list`() = runBlocking {
        val repository: CurrencyRepository = mockk()
        val useCase = GetCryptoCurrenciesUseCase(repository)

        coEvery { repository.getCryptoCurrencies() } returns flowOf(
            listOf(
                CurrencyInfo("BTC", "Bitcoin", "BTC"),
                CurrencyInfo("ETH", "Ethereum", "ETH")
            )
        )

        val result = useCase.execute().first()
        assertEquals(2, result.size)
    }

    @Test
    fun `verify getFiatCurrencies returns fiat currency list`() = runBlocking {
        val repository: CurrencyRepository = mockk()
        val useCase = GetFiatCurrenciesUseCase(repository)

        coEvery { repository.getFiatCurrencies() } returns flowOf(
            listOf(
                CurrencyInfo("USD", "United States Dollar", "USD"),
                CurrencyInfo("SGD", "Singapore Dollar", "SGD"),
                CurrencyInfo("MYR", "Malaysian Ringgit", "MYR")
            )
        )

        val result = useCase.execute().first()
        assertEquals(3, result.size)
    }

    @Test
    fun `verify getAllCurrencies returns all currency list`() = runBlocking {
        val repository: CurrencyRepository = mockk()
        val useCase = GetAllCurrenciesUseCase(repository)

        coEvery { repository.getAllCurrencies() } returns flowOf(
            listOf(
                CurrencyInfo("USD", "United States Dollar", "USD"),
                CurrencyInfo("SGD", "Singapore Dollar", "SGD"),
                CurrencyInfo("MYR", "Malaysian Ringgit", "MYR"),
                CurrencyInfo("BTC", "Bitcoin", "BTC"),
                CurrencyInfo("ETH", "Ethereum", "ETH")
            )
        )

        val result = useCase.execute().first()
        assertEquals(5, result.size)
    }
}