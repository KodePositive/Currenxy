package com.geneus.currenxy.domain.model

data class CurrencyInfo(
    val id: String,
    val name: String,
    val symbol: String,
    val code: String? = null
)