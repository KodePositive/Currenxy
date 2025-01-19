package com.geneus.currenxy.ui.fragments

import android.os.Bundle
import android.view.View
import com.geneus.currenxy.ui.fragments.currencylist.CurrencyListFragment
import com.geneus.currenxy.ui.fragments.currencylist.CurrencyListType

internal class CryptoCurrencyListFragment : CurrencyListFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrencyList(CurrencyListType.CRYPTO)
    }
}