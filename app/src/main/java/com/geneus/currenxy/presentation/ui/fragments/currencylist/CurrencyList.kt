package com.geneus.currenxy.presentation.ui.fragments.currencylist

internal interface CurrencyList {
    fun showLoader()
    fun hideLoader()
    fun showEmptyState()
    fun hideEmptyState()
    fun searchCurrency(search: String)
    fun setCurrencyList(currencyListType: CurrencyListType)
}