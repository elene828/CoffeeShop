package com.example.coffeeshop.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.R
import com.example.coffeeshop.databinding.ViewholderCategoryBinding
import com.example.coffeeshop.model.CategoryModel
import com.example.coffeeshop.model.ItemsModel
import com.squareup.picasso.Picasso

class CategoryAdapter(
    private val items: List<CategoryModel>,
    private val onAddToCartClick: (ItemsModel) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val selectedSizes = mutableMapOf<Int, String>()

    private val priceMap = mapOf(
        1 to mapOf("S" to 2.49, "M" to 2.99, "L" to 3.49),
        2 to mapOf("S" to 3.00, "M" to 3.49, "L" to 3.99),
        3 to mapOf("S" to 3.49, "M" to 3.99, "L" to 4.49),
        4 to mapOf("S" to 3.99, "M" to 4.49, "L" to 4.99)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]


        val currentSize = selectedSizes[position] ?: "M"
        selectedSizes[position] = currentSize

        holder.binding.apply {
            titleCat.text = item.title

            if (item.imageResId != 0) {
                imageView.setImageResource(item.imageResId)
            } else if (item.imageUrl.isNotBlank()) {
                Picasso.get()
                    .load(item.imageUrl)
                    .fit()
                    .centerCrop()
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.placeholder)
            }

            val price = priceMap[item.id]?.get(currentSize) ?: 0.0
            priceTxt.text = "$%.2f".format(price)

            btnSmall.setBackgroundResource(
                if (currentSize == "S") R.drawable.size_button_selected_bg else R.drawable.size_button_bg
            )
            btnMedium.setBackgroundResource(
                if (currentSize == "M") R.drawable.size_button_selected_bg else R.drawable.size_button_bg
            )
            btnLarge.setBackgroundResource(
                if (currentSize == "L") R.drawable.size_button_selected_bg else R.drawable.size_button_bg
            )

            btnSmall.setOnClickListener {
                selectedSizes[position] = "S"
                notifyItemChanged(position)
            }
            btnMedium.setOnClickListener {
                selectedSizes[position] = "M"
                notifyItemChanged(position)
            }
            btnLarge.setOnClickListener {
                selectedSizes[position] = "L"
                notifyItemChanged(position)
            }

            btnAddToCart.setOnClickListener {
                val itemToAdd = ItemsModel(
                    id = item.id,
                    title = item.title,
                    price = price,
                    numberInCart = 1,
                    imageResId = item.imageResId,
                    description = currentSize
                )
                onAddToCartClick(itemToAdd)
            }

            root.setOnClickListener {
                val itemForDetail = ItemsModel(
                    id = item.id,
                    title = item.title,
                    price = price,
                    imageResId = item.imageResId,
                    description = currentSize
                )
                val bundle = Bundle().apply {
                    putParcelable("item", itemForDetail)
                }
                Navigation.findNavController(root).navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root)
}
