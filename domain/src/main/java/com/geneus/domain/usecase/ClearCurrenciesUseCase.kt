package com.geneus.domain.usecase

import com.geneus.domain.repository.CurrencyRepository


class ClearCurrenciesUseCase(private val repository: CurrencyRepository) :
    UseCase<Unit?, Unit> {
    override suspend fun execute(input: Unit?) {
        repository.clearCurrencies()
    }
}