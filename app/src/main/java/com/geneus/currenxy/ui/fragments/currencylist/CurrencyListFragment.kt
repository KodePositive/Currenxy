package com.geneus.currenxy.ui.fragments.currencylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.geneus.currenxy.databinding.FragmentCurrencyListListBinding
import com.geneus.currenxy.ui.DemoSharedViewModel
import com.geneus.currenxy.ui.fragments.currencylist.adapter.CurrencyListRecyclerViewAdapter
import com.geneus.currenxy.util.Status
import com.geneus.domain.model.CurrencyInfo
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


internal open class CurrencyListFragment : Fragment(), CurrencyList {
    private lateinit var binding: FragmentCurrencyListListBinding
    private val viewmodel: CurrencyListViewModel by viewModel()
    private val sharedViewModel: DemoSharedViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearch()
    }

    private fun setSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                /** propagate the search result from [DemoSharedViewModel]*/
                sharedViewModel.searchQuery.collect { query ->
                    searchCurrency(query)
                }
            }
        }
    }

    override fun showLoader() {
        binding.loader.apply {
            visibility = View.VISIBLE
        }
    }

    override fun hideLoader() {
        binding.loader.apply {
            visibility = View.GONE
        }
    }

    override fun showEmptyState() {
        binding.emptyList.emptyListContainer.apply {
            visibility = View.VISIBLE
        }

        binding.rvCurrencies.apply {
            visibility = View.GONE
        }
    }

    override fun hideEmptyState() {
        binding.emptyList.emptyListContainer.apply {
            visibility = View.GONE
        }

        binding.rvCurrencies.apply {
            visibility = View.VISIBLE
        }
    }

    override fun setCurrencyList(currencyListType: CurrencyListType) {
        viewmodel.setType(currencyListType)
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

    open fun showCurrencyList(currencyList: List<CurrencyInfo>?) {
        /** Open this function to override.
         * Provides child classes flexibility to set its own list.
         * */

        if (currencyList.isNullOrEmpty()) {
            showEmptyState()
            return
        }

        hideEmptyState()

        binding.rvCurrencies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CurrencyListRecyclerViewAdapter(currencyList)
        }
    }

    override fun searchCurrency(search: String) {
        viewmodel.setQuery(search)
    }
}
