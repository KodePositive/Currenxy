package com.geneus.currenxy.di

import androidx.room.Room
import com.geneus.currenxy.data.db.AppDatabase
import com.geneus.currenxy.data.service.ApiService
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {
    useCaseModule()
    viewmodelModule()
    dbModule()
    networkModule()
}

private fun Module.useCaseModule() {

}

private fun Module.viewmodelModule() {

}

private fun Module.dbModule() {
    // Database
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "app_database").build() }
    single { get<AppDatabase>().currencyDao() }
}

private fun Module.networkModule() {
    // Network
    single {
        Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}