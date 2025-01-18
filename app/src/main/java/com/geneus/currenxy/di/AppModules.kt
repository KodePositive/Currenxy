package com.geneus.currenxy.di

import androidx.room.Room
import com.geneus.currenxy.data.db.AppDatabase
import com.geneus.currenxy.data.repository.CurrencyRepository
import com.geneus.currenxy.data.repository.CurrencyRepositoryImpl
import com.geneus.currenxy.domain.usecase.ClearCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.InsertCurrenciesUseCase
import com.geneus.currenxy.presentation.ui.DemoSharedViewModel
import com.geneus.currenxy.presentation.ui.fragments.currencylist.CurrencyListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    networkModule()
    dbModule()
    viewmodelModule()

    repoModule()
    useCaseModule()
}

private fun Module.useCaseModule() {
    factory { ClearCurrenciesUseCase(get()) }
    factory { GetCurrenciesUseCase(get()) }
    factory { InsertCurrenciesUseCase(get()) }
}


private fun Module.repoModule() {
    single<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
}

private fun Module.viewmodelModule() {
    viewModel { CurrencyListViewModel(get(), get(), get()) }
    viewModel { DemoSharedViewModel() }
}

private fun Module.dbModule() {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "currenxy_database").build() }
    single { get<AppDatabase>().currencyDao() }
}

private fun Module.networkModule() {
}