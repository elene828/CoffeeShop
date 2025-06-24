package com.example.coffeeshop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coffeeshop.R
import com.example.coffeeshop.databinding.FragmentDetailBinding
import com.example.coffeeshop.model.ItemsModel
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var item: ItemsModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            item = it.getParcelable("item") ?: return@let

            binding.titleTxt.text = item.title
            binding.priceTxt.text = "$${item.price}"

            val imageUrl = item.picUrl.getOrNull(0)
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fit()
                    .centerCrop()
                    .into(binding.coffeeImageView)
            } else {
                binding.coffeeImageView.setImageResource(R.drawable.ic_coffee)
            }

            binding.addToCartBtn.setOnClickListener {
                // TODO: Add to cart logic here
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
