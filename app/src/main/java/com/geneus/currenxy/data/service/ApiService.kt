package com.geneus.currenxy.data.service

import com.geneus.currenxy.domain.model.CurrencyInfo
import retrofit2.http.GET

interface ApiService {
    @GET("currencies")
    suspend fun fetchCurrencies(): List<CurrencyInfo>
}