package com.geneus.currenxy.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geneus.currenxy.data.db.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CurrencyDao {
    @Query("SELECT * FROM currency_info WHERE code is NULL")
    fun getCryptoCurrenciesFlow(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency_info WHERE code != ''")
    fun getFiatCurrenciesFlow(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currency_info")
    fun getAllCurrenciesFlow(): Flow<List<CurrencyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM currency_info")
    fun clear()
}