package com.geneus.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geneus.data.db.dao.CurrencyDao
import com.geneus.data.db.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}


