package com.geneus.currenxy.domain.usecase

import com.geneus.currenxy.data.db.CurrencyType
import com.geneus.currenxy.data.repository.CurrencyRepository
import com.geneus.currenxy.domain.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow

class GetCurrenciesUseCase(private val repository: CurrencyRepository) :
    UseCase<CurrencyType, Flow<List<CurrencyInfo>>> {
    override suspend fun execute(input: CurrencyType): Flow<List<CurrencyInfo>> {
        return repository.getCurrenciesFlow(input)
    }
}