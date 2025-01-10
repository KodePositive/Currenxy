package com.geneus.currenxy.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.geneus.currenxy.databinding.ActivityDemoBinding
import com.geneus.currenxy.presentation.ui.currencylist.CurrencyListFragment
import com.geneus.currenxy.presentation.ui.currencylist.CurrencyListViewModel
import com.geneus.currenxy.util.AssetsUtil
import com.geneus.currenxy.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class DemoActivity : AppCompatActivity() {
    companion object {
        const val FRAGMENT_CRYPTO_LIST = "FRAGMENT_CRYPTO_LIST"
        const val FRAGMENT_FIAT_LIST = "FRAGMENT_FIAT_LIST"
        const val FRAGMENT_ALL_LIST = "FRAGMENT_ALL_LIST"
    }

    private lateinit var binding: ActivityDemoBinding
    private val viewmodel: CurrencyListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClearDb.setOnClickListener {
            clearDatabase()
        }

        binding.btnAddToDb.setOnClickListener {
            insertToDatabase()
        }

        binding.btnGotoCrytoList.setOnClickListener {
            setFragment(FRAGMENT_CRYPTO_LIST)
        }

        binding.btnGotoFiatList.setOnClickListener {
            setFragment(FRAGMENT_FIAT_LIST)
        }

        binding.btnGotoFiatList.setOnClickListener {
            setFragment(FRAGMENT_ALL_LIST)
        }

        lifecycleScope.launch {
            viewmodel.uiState
                .collect { result ->
                    when (result.status) {
                        Status.ERROR -> {
                            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                            Log.d("DemoActivity", "Error: ")
                        }

                        Status.LOADING -> {
                            Toast.makeText(applicationContext, "Loading", Toast.LENGTH_SHORT).show()
                            Log.d("DemoActivity", "Loading: ")
                        }

                        Status.SUCCESS -> {
                            if (result.data?.isNotEmpty() == true)
                                Toast.makeText(
                                    applicationContext,
                                    "${result.data.first()}",
                                    Toast.LENGTH_SHORT
                                ).show()

                            Log.d("DemoActivity", "Success: ")
                        }
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
                    AssetsUtil.readJsonFromAssets(
                        context = applicationContext,
                        fileName = "listA.json"
                    )
                )

                //inserts crypto list: listA.json
                viewmodel.insertCurrencies(
                    AssetsUtil.readJsonFromAssets(
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
                    FRAGMENT_CRYPTO_LIST -> fragmentTransaction.add(
                        binding.container, CurrencyListFragment(), FRAGMENT_CRYPTO_LIST
                    )

                    FRAGMENT_FIAT_LIST -> fragmentTransaction.add(
                        binding.container, CurrencyListFragment(), FRAGMENT_FIAT_LIST
                    )

                    FRAGMENT_ALL_LIST -> {
                        fragmentTransaction.add(
                            binding.container,
                            CurrencyListFragment(),
                            FRAGMENT_ALL_LIST
                        )
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
}