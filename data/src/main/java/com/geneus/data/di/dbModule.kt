package com.geneus.data.di

import androidx.room.Room
import com.geneus.data.db.AppDatabase
import org.koin.core.module.Module

fun Module.dbModule() {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "currenxy_database").build() }
    single { get<AppDatabase>().currencyDao() }
}