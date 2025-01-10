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

    override suspend fun getCryptoCurrencies(): Flow<List<CurrencyInfo>> {
        return dao.getCrytoCurrenciesFlow().map { entities ->
            entities.map { CurrencyInfo(it.id, it.name, it.symbol, it.code) }
        }
    }

    override suspend fun getFiatCurrencies(): Flow<List<CurrencyInfo>> {
        return dao.getFiatCurrenciesFlow().map { entities ->
            entities.map { CurrencyInfo(it.id, it.name, it.symbol, it.code) }
        }
    }

    override suspend fun getAllCurrencies(): Flow<List<CurrencyInfo>> {
        return dao.getAllCurrenciesFlow().map { entities ->
            entities.map { CurrencyInfo(it.id, it.name, it.symbol, it.code) }
        }
    }

    private fun getCurrencyType(code: String?): CurrencyType {
        return if (code.isNullOrEmpty()) {
            CurrencyType.CRYPTO
        } else {
            CurrencyType.FIAT
        }
    }
}