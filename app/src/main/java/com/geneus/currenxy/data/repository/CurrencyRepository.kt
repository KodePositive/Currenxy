package com.geneus.currenxy.data.repository

import com.geneus.currenxy.data.db.CurrencyType
import com.geneus.currenxy.domain.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrenciesFlow(type: CurrencyType): Flow<List<CurrencyInfo>>
    suspend fun insertCurrencies(currencies: List<CurrencyInfo>)
    suspend fun clearCurrencies()
}