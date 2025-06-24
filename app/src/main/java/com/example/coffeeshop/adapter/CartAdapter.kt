package com.example.coffeeshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.databinding.ViewholderCartBinding
import com.example.coffeeshop.model.ItemsModel
import com.squareup.picasso.Picasso

class CartAdapter(
    private var items: MutableList<ItemsModel>,
    private val onPlusClick: (ItemsModel) -> Unit,
    private val onMinusClick: (ItemsModel) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsModel) {
            binding.titleTxt.text = item.title
            binding.priceTxt.text = "$${String.format("%.2f", item.price * item.numberInCart)}"
            binding.quantityTxt.text = item.numberInCart.toString()

            binding.btnPlus.setOnClickListener { onPlusClick(item) }
            binding.btnMinus.setOnClickListener { onMinusClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<ItemsModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
