package com.geneus.domain.usecase

import com.geneus.domain.model.CurrencyInfo
import com.geneus.domain.repository.CurrencyRepository

class InsertCurrenciesUseCase(private val repository: CurrencyRepository) :
    UseCase<List<CurrencyInfo>, Unit> {
    override suspend fun execute(input: List<CurrencyInfo>?) {
        if(input == null) return

        repository.insertCurrencies(input)
    }
}