package com.geneus.currenxy.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.geneus.currenxy.R
import com.geneus.currenxy.databinding.ActivityDemoBinding
import com.geneus.currenxy.ui.fragments.AllCurrencyListFragment
import com.geneus.currenxy.ui.fragments.CryptoCurrencyListFragment
import com.geneus.currenxy.ui.fragments.FiatCurrencyListFragment
import com.geneus.currenxy.ui.fragments.currencylist.CurrencyListType
import com.geneus.currenxy.ui.fragments.currencylist.CurrencyListViewModel
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
        setFragment(CurrencyListType.CRYPTO)
        binding.bottomBar.onItemSelected = {
            when (it) {
                0 -> {
                    setFragment(CurrencyListType.CRYPTO)
                }

                1 -> {
                    setFragment(CurrencyListType.FIAT)
                }

                2 -> {
                    setFragment(CurrencyListType.ALL)
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

    private fun setFragment(currencyListType: CurrencyListType) {
        binding.container.apply {
            visibility = View.VISIBLE
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentToShow = supportFragmentManager.findFragmentByTag(currencyListType.name)

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
                when (currencyListType) {
                    CurrencyListType.CRYPTO -> {
                        fragmentTransaction.add(
                            binding.container,
                            CryptoCurrencyListFragment(),
                            currencyListType.name
                        ) //tag: CRYPTO
                    }

                    CurrencyListType.FIAT -> {
                        fragmentTransaction.add(
                            binding.container,
                            FiatCurrencyListFragment(),
                            currencyListType.name
                        ) //tag: FIAT
                    }

                    CurrencyListType.ALL -> {
                        fragmentTransaction.add(
                            binding.container,
                            AllCurrencyListFragment(),
                            currencyListType.name
                        ) //tag: ALL
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
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                sharedVm.setSearchQuery(newText?: "")
                return false
            }
        })
    }
}