package com.geneus.domain.usecase

import com.geneus.domain.model.CurrencyInfo
import com.geneus.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetAllCurrenciesUseCase(private val repository: CurrencyRepository) :
    UseCase<Unit?, Flow<List<CurrencyInfo>>> {
    override suspend fun execute(input: Unit?): Flow<List<CurrencyInfo>> {
        return repository.getAllCurrencies()
    }
}