package com.geneus.currenxy.util

import com.geneus.currenxy.domain.model.CurrencyInfo

sealed class CurrencyUiState {
    data object Loading : CurrencyUiState()
    data class Success(val currencies: List<CurrencyInfo>) : CurrencyUiState()
    data class Error(val message: String) : CurrencyUiState()
}