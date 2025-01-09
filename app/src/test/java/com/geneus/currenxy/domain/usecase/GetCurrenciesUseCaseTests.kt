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
    fun `verify getCurrenciesUseCase returns filtered list`() = runBlocking {
        val repository: CurrencyRepository = mockk()
        val useCase = GetCurrenciesUseCase(repository)

        coEvery { repository.getCurrenciesFlow(CurrencyType.CRYPTO) } returns flowOf(
            listOf(
                CurrencyInfo("BTC", "Bitcoin", "BTC"),
                CurrencyInfo("ETH", "Ethereum", "ETH")
            )
        )

        val result = useCase.execute(CurrencyType.CRYPTO).first()
        assertEquals(2, result.size)
    }
}