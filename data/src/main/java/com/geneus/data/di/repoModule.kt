package com.geneus.data.di

import com.geneus.data.repository.CurrencyRepositoryImpl
import com.geneus.domain.repository.CurrencyRepository
import org.koin.core.module.Module

fun Module.repoModule() {
    //provides concrete repository impl.
    single<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
}