package com.geneus.domain.usecase

import com.geneus.domain.model.CurrencyInfo
import com.geneus.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetCryptoCurrenciesUseCase(private val repository: CurrencyRepository) :
    UseCase<Unit?, Flow<List<CurrencyInfo>>> {
    override suspend fun execute(input: Unit?): Flow<List<CurrencyInfo>> {
        return repository.getCryptoCurrencies()
    }
}