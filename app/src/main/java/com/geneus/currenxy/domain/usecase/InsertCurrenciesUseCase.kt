package com.geneus.currenxy.domain.usecase

import com.geneus.currenxy.data.repository.CurrencyRepository
import com.geneus.currenxy.domain.model.CurrencyInfo

class InsertCurrenciesUseCase(private val repository: CurrencyRepository) :
    UseCase<List<CurrencyInfo>, Unit> {
    override suspend fun execute(input: List<CurrencyInfo>) {
        repository.insertCurrencies(input)
    }
}