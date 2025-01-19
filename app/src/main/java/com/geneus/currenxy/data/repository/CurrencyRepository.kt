package com.geneus.currenxy.data.repository

import com.geneus.currenxy.domain.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow

internal interface CurrencyRepository {
    suspend fun insertCurrencies(currencies: List<CurrencyInfo>)
    suspend fun clearCurrencies()
    suspend fun getCryptoCurrencies(): Flow<List<CurrencyInfo>>
    suspend fun getFiatCurrencies(): Flow<List<CurrencyInfo>>
    suspend fun getAllCurrencies(): Flow<List<CurrencyInfo>>
}