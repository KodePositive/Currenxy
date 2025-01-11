package com.geneus.currenxy.util

import android.content.Context
import com.geneus.currenxy.domain.model.CurrencyInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

object AssetsUtil {
    fun <T> readJsonFromAssets( context: Context, fileName: String): List<T> {
        val inputStream = context.assets.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val jsonString = bufferedReader.use { it.readText() }
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}