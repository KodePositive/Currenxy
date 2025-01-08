package com.geneus.currenxy.di

import androidx.room.Room
import com.geneus.currenxy.data.db.AppDatabase
import com.geneus.currenxy.data.repository.CurrencyRepository
import com.geneus.currenxy.data.repository.CurrencyRepositoryImpl
import com.geneus.currenxy.data.service.ApiService
import com.geneus.currenxy.domain.usecase.ClearCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.InsertCurrenciesUseCase
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {
    networkModule()
    dbModule()
    viewmodelModule()

    repoModule()
    useCaseModule()
}

private fun Module.useCaseModule() {
    single { ClearCurrenciesUseCase(get()) }
    single { GetCurrenciesUseCase(get()) }
    single { InsertCurrenciesUseCase(get()) }
}


private fun Module.repoModule() {
    single<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
}

private fun Module.viewmodelModule() {

}

private fun Module.dbModule() {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "currenxy_database").build() }
    single { get<AppDatabase>().currencyDao() }
}

private fun Module.networkModule() {
    single {
        Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}