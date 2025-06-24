package com.example.coffeeshop.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.R
import com.example.coffeeshop.databinding.ViewholderPopularBinding
import com.example.coffeeshop.model.ItemsModel
import com.squareup.picasso.Picasso

class PopularAdapter(
    private var items: MutableList<ItemsModel>,
    private val onAddToCart: (ItemsModel) -> Unit
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderPopularBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.binding.ptitleTxt.text = item.title
        holder.binding.ppriceTxt.text = "$${item.price}"

        val imageUrl = item.picUrl.getOrNull(0)
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fit()
                .centerCrop()
                .into(holder.binding.shapeableImageView)
        } else {
            holder.binding.shapeableImageView.setImageResource(R.drawable.placeholder)
        }

        holder.binding.btnAddToCart.setOnClickListener {
            onAddToCart(item)
            Toast.makeText(holder.itemView.context, "${item.title} added to cart", Toast.LENGTH_SHORT).show()
        }

        holder.binding.root.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("item", item)
            }
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ItemsModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ViewholderPopularBinding) :
        RecyclerView.ViewHolder(binding.root)
}
