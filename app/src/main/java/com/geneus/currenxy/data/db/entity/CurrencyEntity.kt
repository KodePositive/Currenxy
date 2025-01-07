package com.geneus.currenxy.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geneus.currenxy.data.db.CurrencyType

@Entity(tableName = "currency_info")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val code: String? = null,
    val type: CurrencyType? = null
)
