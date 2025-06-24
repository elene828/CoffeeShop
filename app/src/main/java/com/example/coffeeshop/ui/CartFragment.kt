package com.example.coffeeshop.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshop.adapter.CartAdapter
import com.example.coffeeshop.data.AppDatabase
import com.example.coffeeshop.databinding.FragmentCartBinding
import com.example.coffeeshop.helper.CartUpdateListener
import com.example.coffeeshop.helper.ManagmentCart
import com.example.coffeeshop.model.ItemsModel

class CartFragment : Fragment(), CartUpdateListener {

    private lateinit var managmentCart: ManagmentCart
    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: FragmentCartBinding  // assuming ViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        managmentCart = ManagmentCart(requireContext(), AppDatabase.getInstance(requireContext()))
        managmentCart.cartUpdateListener = this

        cartAdapter = CartAdapter(mutableListOf(),
            onPlusClick = { item -> managmentCart.plusItem(item) },
            onMinusClick = { item -> managmentCart.minusItem(item) }
        )

        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    override fun onCartUpdated(updatedCart: List<ItemsModel>) {
        cartAdapter.updateItems(updatedCart)
        val total = managmentCart.getTotalFee()
        binding.tvCartTotal.text = "Total: $${String.format("%.2f", total)}"
    }
}
