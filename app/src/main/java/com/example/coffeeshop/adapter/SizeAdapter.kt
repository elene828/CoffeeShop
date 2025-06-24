package com.example.coffeeshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.R
import com.example.coffeeshop.databinding.ViewholderSizeBinding

class SizeAdapter(
    private val sizes: List<String>,
    private val onSizeSelected: (selectedSize: String) -> Unit
) : RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val size = sizes[position]
        holder.bind(size, position == selectedPosition)

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            onSizeSelected(size)
        }
    }

    override fun getItemCount() = sizes.size

    inner class ViewHolder(private val binding: ViewholderSizeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(size: String, isSelected: Boolean) {
            binding.sizeTxt.text = size

            val bgColor = if (isSelected) {
                ContextCompat.getColor(binding.root.context, R.color.selected_size_bg)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.default_size_bg)
            }
            binding.root.setBackgroundColor(bgColor)

            val textColor = if (isSelected) {
                ContextCompat.getColor(binding.root.context, R.color.white)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.black)
            }
            binding.sizeTxt.setTextColor(textColor)
        }
    }
}
