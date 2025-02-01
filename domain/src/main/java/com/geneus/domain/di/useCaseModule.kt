package com.geneus.domain.di

import com.geneus.domain.usecase.ClearCurrenciesUseCase
import com.geneus.domain.usecase.GetAllCurrenciesUseCase
import com.geneus.domain.usecase.GetCryptoCurrenciesUseCase
import com.geneus.domain.usecase.GetFiatCurrenciesUseCase
import com.geneus.domain.usecase.InsertCurrenciesUseCase
import org.koin.core.module.Module

fun Module.useCaseModule() {
    //provides all app usecases.
    factory { ClearCurrenciesUseCase(get()) }
    factory { InsertCurrenciesUseCase(get()) }
    factory { GetCryptoCurrenciesUseCase(get()) }
    factory { GetFiatCurrenciesUseCase(get()) }
    factory { GetAllCurrenciesUseCase(get()) }
}