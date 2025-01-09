package com.geneus.currenxy.data.repository

import com.geneus.currenxy.data.db.CurrencyType
import com.geneus.currenxy.data.db.dao.CurrencyDao
import com.geneus.currenxy.data.db.entity.CurrencyEntity
import com.geneus.currenxy.domain.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyRepositoryImpl(private val dao: CurrencyDao) : CurrencyRepository {
    override suspend fun getCurrenciesFlow(type: CurrencyType): Flow<List<CurrencyInfo>> {
        return dao.getCurrenciesFlow(type).map { entities ->
            entities.map { CurrencyInfo(it.id, it.name, it.symbol, it.code) }
        }
    }

    override suspend fun insertCurrencies(currencies: List<CurrencyInfo>) {
        dao.insertAll(currencies.map {
            CurrencyEntity(it.id, it.name, it.symbol, it.code, type = getCurrencyType(it.code))
        })
    }

    override suspend fun clearCurrencies() {
        dao.clear()
    }

    private fun getCurrencyType(code: String?): CurrencyType {
        return if (code.isNullOrEmpty()) {
            CurrencyType.CRYPTO
        } else {
            CurrencyType.FIAT
        }
    }
}