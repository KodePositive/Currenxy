package com.geneus.currenxy.presentation.ui.fragments.currencylist

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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

        binding.etSearch.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    searchCurrency(s.toString())
                }

                override fun afterTextChanged(s: Editable) {

                }
            })
        }

        binding.ivCancel.apply {
            setOnClickListener {
                clearSearch()
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
        if (currencyList.isNullOrEmpty()) {
            showEmptyState()
            return
        }

        removeEmptyState()

        binding.rvCurrencies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CurrencyListRecyclerViewAdapter(currencyList)
        }
    }

    open fun searchCurrency(search: String) {
        viewmodel.setQuery(search)
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun clearSearch() {
        binding.etSearch.text.clear()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        binding.etSearch.clearFocus()
        hideKeyboard()
    }
}
