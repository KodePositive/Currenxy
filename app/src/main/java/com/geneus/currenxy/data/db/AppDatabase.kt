package com.geneus.currenxy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geneus.currenxy.data.db.dao.CurrencyDao
import com.geneus.currenxy.data.db.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}


