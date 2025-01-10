package com.geneus.currenxy.presentation.ui.fragments.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.geneus.currenxy.databinding.FragmentCurrencyListListBinding
import com.geneus.currenxy.domain.model.CurrencyInfo
import com.geneus.currenxy.presentation.ui.fragments.currencylist.adapter.CurrencyListRecyclerViewAdapter
import com.geneus.currenxy.util.Status
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

open class CurrencyListFragment : Fragment() {
    companion object {
        const val FRAGMENT_CRYPTO_LIST = "FRAGMENT_CRYPTO_LIST"
        const val FRAGMENT_FIAT_LIST = "FRAGMENT_FIAT_LIST"
        const val FRAGMENT_ALL_LIST = "FRAGMENT_ALL_LIST"
    }

    internal lateinit var binding: FragmentCurrencyListListBinding
    internal val viewmodel: CurrencyListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUiState()
    }

    private fun getUiState() {
        lifecycleScope.launch {
            viewmodel.uiState
                .collect { result ->
                    when (result.status) {
                        Status.ERROR -> {
                            hideLoader()
                        }

                        Status.LOADING -> {
                            showLoader()
                        }

                        Status.SUCCESS -> {
                            hideLoader()
                            showCurrencyList(result.data)
                        }
                    }
                }
        }
    }

    open fun showLoader() {
        binding.loader.apply {
            visibility = View.VISIBLE
        }
    }

    open fun hideLoader() {
        binding.loader.apply {
            visibility = View.GONE
        }
    }

    open fun showEmptyState() {
        binding.emptyList.emptyListContainer.apply {
            visibility = View.VISIBLE
        }

        binding.rvCurrencies.apply {
            visibility = View.GONE
        }
    }

    open fun removeEmptyState() {
        binding.emptyList.emptyListContainer.apply {
            visibility = View.GONE
        }

        binding.rvCurrencies.apply {
            visibility = View.VISIBLE
        }
    }

    open fun showCurrencyList(currencyList: List<CurrencyInfo>?) {
        if(currencyList.isNullOrEmpty()) {
            showEmptyState()
            return
        }

        removeEmptyState()

        binding.rvCurrencies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CurrencyListRecyclerViewAdapter(currencyList)
        }
    }
}