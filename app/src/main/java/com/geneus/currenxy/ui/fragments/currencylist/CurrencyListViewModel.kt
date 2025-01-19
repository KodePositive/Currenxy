package com.geneus.currenxy.ui.fragments.currencylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geneus.currenxy.domain.model.CurrencyInfo
import com.geneus.currenxy.domain.usecase.ClearCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetAllCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetCryptoCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.GetFiatCurrenciesUseCase
import com.geneus.currenxy.domain.usecase.InsertCurrenciesUseCase
import com.geneus.currenxy.util.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class CurrencyListViewModel(
    private val insertCurrenciesUseCase: InsertCurrenciesUseCase,
    private val clearCurrenciesUseCase: ClearCurrenciesUseCase,
    private val getCryptoCurrenciesUseCase: GetCryptoCurrenciesUseCase,
    private val getFiatCurrenciesUseCase: GetFiatCurrenciesUseCase,
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CurrencyInfo>>>(UiState.loading(null))
    val uiState: StateFlow<UiState<List<CurrencyInfo>>> = _uiState

    private val _query = MutableStateFlow("")
    private val _type = MutableStateFlow(CurrencyListType.CRYPTO)

    init {
        viewModelScope.launch {
            combine(_type, _query.debounce(300).map { it.trim() }) { type, query ->
                when (type) {
                    CurrencyListType.CRYPTO -> getCryptoCurrenciesUseCase
                    CurrencyListType.FIAT -> getFiatCurrenciesUseCase
                    CurrencyListType.ALL -> getAllCurrenciesUseCase
                }.execute(Unit).map { list ->
                    list.filter {
                        it.name.startsWith(query, true) ||
                                it.symbol.startsWith(query, true) ||
                                it.code?.startsWith(query, true) == true ||
                                it.name.contains(" $query", true)
                    }
                }
            }
                .flattenMerge()
                .onStart {
                    emit(emptyList())
                    _uiState.value = UiState.loading(null)
                }
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

    fun setType(currencyListType: CurrencyListType) {
        _type.value = currencyListType
    }

    suspend fun insertCurrencies(currencies: List<CurrencyInfo>) {
        insertCurrenciesUseCase.execute(currencies)
    }

    suspend fun clearCurrencies() {
        clearCurrenciesUseCase.execute(Unit)
    }
}
