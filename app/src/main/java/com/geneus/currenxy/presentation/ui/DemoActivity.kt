package com.geneus.currenxy.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.geneus.currenxy.databinding.ActivityDemoBinding
import com.geneus.currenxy.util.AssetsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class DemoActivity : AppCompatActivity() {
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
}