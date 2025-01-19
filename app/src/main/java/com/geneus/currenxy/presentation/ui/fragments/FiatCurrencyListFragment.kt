package com.geneus.currenxy.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import com.geneus.currenxy.presentation.ui.fragments.currencylist.CurrencyListFragment
import com.geneus.currenxy.presentation.ui.fragments.currencylist.CurrencyListType

internal class FiatCurrencyListFragment : CurrencyListFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrencyList(CurrencyListType.FIAT)
    }
}