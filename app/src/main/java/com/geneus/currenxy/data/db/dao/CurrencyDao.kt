package com.geneus.currenxy.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geneus.currenxy.data.db.CurrencyType
import com.geneus.currenxy.data.db.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency_info WHERE type = :type")
    fun getCurrenciesFlow(type: CurrencyType): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency_info WHERE type = 'CRYPTO'")
    fun getCrytoCurrenciesFlow(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency_info WHERE type = 'FIAT'")
    fun getFiatCurrenciesFlow(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency_info")
    fun getAllCurrenciesFlow(): Flow<List<CurrencyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM currency_info")
    fun clear()
}