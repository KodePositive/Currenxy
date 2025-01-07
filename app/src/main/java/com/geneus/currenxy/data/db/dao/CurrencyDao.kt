package com.geneus.currenxy.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geneus.currenxy.data.db.CurrencyType
import com.geneus.currenxy.data.db.entity.CurrencyEntity

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency_info WHERE type = :type")
    suspend fun getCurrencies(type: CurrencyType): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM currency_info")
    suspend fun clear()
}