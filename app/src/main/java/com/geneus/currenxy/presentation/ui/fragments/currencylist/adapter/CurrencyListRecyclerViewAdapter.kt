package com.geneus.currenxy.presentation.ui.fragments.currencylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geneus.currenxy.databinding.CurrencyItemBinding
import com.geneus.currenxy.domain.model.CurrencyInfo

internal class CurrencyListRecyclerViewAdapter(
    private val values: List<CurrencyInfo>
) : RecyclerView.Adapter<CurrencyListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            CurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: CurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}