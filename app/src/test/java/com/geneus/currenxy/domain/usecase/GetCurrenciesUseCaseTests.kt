package com.geneus.currenxy.domain.usecase

import com.geneus.currenxy.data.db.CurrencyType
import com.geneus.currenxy.data.repository.CurrencyRepository
import com.geneus.currenxy.domain.model.CurrencyInfo
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetCurrenciesUseCaseTests() {
    @Test
    fun `verify getCryptoCurrencies returns cryptocurrency list`() = runBlocking {
        val repository: CurrencyRepository = mockk()
        val useCase = GetCurrenciesUseCase(repository)

        coEvery { repository.getCryptoCurrencies() } returns flowOf(
            listOf(
                CurrencyInfo("BTC", "Bitcoin", "BTC"),
                CurrencyInfo("ETH", "Ethereum", "ETH")
            )
        )

        val result = useCase.execute(CurrencyType.CRYPTO).first()
        assertEquals(2, result.size)
    }

    @Test
    fun `verify getFiatCurrencies returns fiatcurrency list`() = runBlocking {
        val repository: CurrencyRepository = mockk()
        val useCase = GetCurrenciesUseCase(repository)

        coEvery { repository.getFiatCurrencies() } returns flowOf(
            listOf(
                CurrencyInfo("USD", "United States Dollar", "USD"),
                CurrencyInfo("SGD", "Singapore Dollar", "SGD"),
                CurrencyInfo("MYR", "Malaysian Ringgit", "MYR")
            )
        )

        val result = useCase.execute(CurrencyType.FIAT).first()
        assertEquals(3, result.size)
    }

    @Test
    fun `verify getAllCurrencies returns all currency list`() = runBlocking {
        val repository: CurrencyRepository = mockk()
        val useCase = GetCurrenciesUseCase(repository)

        coEvery { repository.getAllCurrencies() } returns flowOf(
            listOf(
                CurrencyInfo("USD", "United States Dollar", "USD"),
                CurrencyInfo("SGD", "Singapore Dollar", "SGD"),
                CurrencyInfo("MYR", "Malaysian Ringgit", "MYR"),
                CurrencyInfo("BTC", "Bitcoin", "BTC"),
                CurrencyInfo("ETH", "Ethereum", "ETH")
            )
        )

        val result = useCase.execute(CurrencyType.ALL).first()
        assertEquals(5, result.size)
    }
}