package com.geneus.domain.repository

import com.geneus.domain.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun insertCurrencies(currencies: List<CurrencyInfo>)
    suspend fun clearCurrencies()
    suspend fun getCryptoCurrencies(): Flow<List<CurrencyInfo>>
    suspend fun getFiatCurrencies(): Flow<List<CurrencyInfo>>
    suspend fun getAllCurrencies(): Flow<List<CurrencyInfo>>
}