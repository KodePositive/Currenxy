package com.geneus.currenxy.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.geneus.currenxy.R
import com.geneus.currenxy.databinding.ActivityDemoBinding
import com.geneus.currenxy.presentation.ui.fragments.AllCurrencyListFragment
import com.geneus.currenxy.presentation.ui.fragments.CryptoCurrencyListFragment
import com.geneus.currenxy.presentation.ui.fragments.FiatCurrencyListFragment
import com.geneus.currenxy.presentation.ui.fragments.currencylist.CurrencyListFragment.Companion.FRAGMENT_ALL_LIST
import com.geneus.currenxy.presentation.ui.fragments.currencylist.CurrencyListFragment.Companion.FRAGMENT_CRYPTO_LIST
import com.geneus.currenxy.presentation.ui.fragments.currencylist.CurrencyListFragment.Companion.FRAGMENT_FIAT_LIST
import com.geneus.currenxy.presentation.ui.fragments.currencylist.CurrencyListViewModel
import com.geneus.currenxy.util.AssetsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding
    private val viewmodel: CurrencyListViewModel by viewModel()
    private val sharedVm: DemoSharedViewModel by viewModel()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_top_overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuClear -> {
                clearDatabase()
                true
            }

            R.id.menuInsert -> {
                insertToDatabase()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSearchView()
        setFragment(FRAGMENT_CRYPTO_LIST)
        binding.bottomBar.onItemSelected = {
            when (it) {
                0 -> {
                    setFragment(FRAGMENT_CRYPTO_LIST)
                }

                1 -> {
                    setFragment(FRAGMENT_FIAT_LIST)
                }

                2 -> {
                    setFragment(FRAGMENT_ALL_LIST)
                }
            }
        }
    }

    private fun clearDatabase() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                viewmodel.clearCurrencies()
            }
        }
    }

    private fun insertToDatabase() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                //inserts crypto list: listA.json
                viewmodel.insertCurrencies(
                    AssetsUtil.getCurrencyListFromJson(
                        context = applicationContext,
                        fileName = "listA.json"
                    )
                )

                //inserts crypto list: listA.json
                viewmodel.insertCurrencies(
                    AssetsUtil.getCurrencyListFromJson(
                        context = applicationContext,
                        fileName = "listB.json"
                    )
                )
            }
        }
    }

    private fun setFragment(fragmentToShowTag: String) {
        binding.container.apply {
            visibility = View.VISIBLE
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentToShow = supportFragmentManager.findFragmentByTag(fragmentToShowTag)

        /**
         * show the intended fragment
         * */
        when (fragmentToShow != null) {
            true -> {
                fragmentTransaction.show(fragmentToShow)
            }

            else -> {
                /**
                 * add the fragment to the manager if it doesn't not exist.
                 * */
                when (fragmentToShowTag) {
                    FRAGMENT_CRYPTO_LIST -> {
                        fragmentTransaction.add(binding.container, CryptoCurrencyListFragment(), FRAGMENT_CRYPTO_LIST)
                    }

                    FRAGMENT_FIAT_LIST -> {
                        fragmentTransaction.add(binding.container, FiatCurrencyListFragment(), FRAGMENT_FIAT_LIST)
                    }

                    FRAGMENT_ALL_LIST -> {
                        fragmentTransaction.add(binding.container, AllCurrencyListFragment(), FRAGMENT_ALL_LIST)
                    }
                }
            }
        }

        /**
         * hide all other fragments - except the fragment to show.
         * */
        for (fragment in fragmentManager.fragments) {
            if (fragment != fragmentToShow)
                fragmentTransaction.hide(fragment)
        }

        fragmentTransaction.commit()
    }

    private fun setSearchView() {
        binding.ivCancel.apply {
            setOnClickListener {
                clearSearch()
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
                    sharedVm.setSearchQuery(s.toString())
                }

                override fun afterTextChanged(s: Editable) {

                }
            })
        }
    }

    private fun clearSearch() {
        binding.etSearch.text.clear()
    }
}