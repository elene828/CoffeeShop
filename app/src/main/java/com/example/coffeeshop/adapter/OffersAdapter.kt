package com.example.coffeeshop.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeshop.databinding.ViewholderOfferBinding
import com.example.coffeeshop.model.ItemsModel


class OffersAdapter(val items: List<ItemsModel>) :
    RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = "$${item.price}"

        Glide.with(holder.itemView.context)
            .load(item.picUrl.getOrNull(0))
            .into(holder.binding.shapeableImageView)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ViewholderOfferBinding) :
        RecyclerView.ViewHolder(binding.root)
}