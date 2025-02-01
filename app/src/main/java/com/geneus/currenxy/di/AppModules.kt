package com.geneus.currenxy.di

import com.geneus.currenxy.ui.DemoSharedViewModel
import com.geneus.currenxy.ui.fragments.currencylist.CurrencyListViewModel
import com.geneus.data.di.dbModule
import com.geneus.data.di.repoModule
import com.geneus.domain.di.useCaseModule
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

private fun Module.viewmodelModule() {
    viewModel { CurrencyListViewModel(get(), get(), get(), get(), get()) }
    viewModel { DemoSharedViewModel() }
}

private fun Module.networkModule() {
}