package com.geneus.currenxy.presentation.ui.fragments.currencylist

import com.geneus.currenxy.data.db.CurrencyType

interface CurrencyList {
    fun showLoader()
    fun hideLoader()
    fun showEmptyState()
    fun hideEmptyState()
    fun searchCurrency(search: String)
    fun clearSearch()
    fun setCurrencyList(currencyType: CurrencyType)
}