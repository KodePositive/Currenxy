package com.geneus.currenxy.ui.fragments.currencylist

internal interface CurrencyList {
    fun showLoader()
    fun hideLoader()
    fun showEmptyState()
    fun hideEmptyState()
    fun searchCurrency(search: String)
    fun setCurrencyList(currencyListType: CurrencyListType)
}