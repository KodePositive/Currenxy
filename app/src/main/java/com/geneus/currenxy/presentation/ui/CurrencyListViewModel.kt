package com.geneus.currenxy.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geneus.currenxy.data.db.CurrencyType
import com.geneus.currenxy.domain.model.CurrencyInfo
import com.geneus.currenxy.domain.usecase.ClearCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.InsertCurrenciesUseCase
import com.geneus.currenxy.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val insertCurrenciesUseCase: InsertCurrenciesUseCase,
    private val clearCurrenciesUseCase: ClearCurrenciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CurrencyInfo>>>(UiState.loading(null))
    val uiState: StateFlow<UiState<List<CurrencyInfo>>> = _uiState

    private val _query = MutableStateFlow("")
    private val _type = MutableStateFlow(CurrencyType.CRYPTO)

    init {
        viewModelScope.launch {
            combine(_type, _query) { type, query ->
                getCurrenciesUseCase.execute(type).map { list ->
                    list.filter {
                        it.name.startsWith(query, true) ||
                                it.symbol.startsWith(query, true) ||
                                it.name.contains(" $query", true)
                    }
                }
            }
                .flattenMerge()
                .onStart { emit(emptyList()) }
                .catch { exception ->
                    _uiState.value = UiState.error(exception.message ?: "Unknown error", null)
                }
                .collect { result ->
                    _uiState.value = UiState.success(result)
                }
        }
    }

    fun setQuery(query: String) {
        _query.value = query
    }

    fun setType(currencyType: CurrencyType) {
        _type.value = currencyType
    }

    suspend fun insertCurrencies(currencies: List<CurrencyInfo>) {
        insertCurrenciesUseCase.execute(currencies)
    }

    suspend fun clearCurrencies() {
        clearCurrenciesUseCase.execute(Unit)
    }
}
