package com.geneus.currenxy.domain.usecase

import com.geneus.currenxy.data.repository.CurrencyRepository
import com.geneus.currenxy.domain.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow

internal class GetAllCurrenciesUseCase(private val repository: CurrencyRepository) :
    UseCase<Unit?, Flow<List<CurrencyInfo>>> {
    override suspend fun execute(input: Unit?): Flow<List<CurrencyInfo>> {
        return repository.getAllCurrencies()
    }
}