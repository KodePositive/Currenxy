package com.geneus.currenxy.domain.usecase

import com.geneus.currenxy.data.repository.CurrencyRepository

class ClearCurrenciesUseCase(private val repository: CurrencyRepository) : UseCase<Unit, Unit> {
    override suspend fun execute(input: Unit) {
        repository.clearCurrencies()
    }
}