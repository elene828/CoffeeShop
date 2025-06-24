package com.example.coffeeshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshop.R
import com.example.coffeeshop.adapter.CategoryAdapter
import com.example.coffeeshop.adapter.PopularAdapter
import com.example.coffeeshop.adapter.SizeAdapter
import com.example.coffeeshop.data.AppDatabase
import com.example.coffeeshop.databinding.FragmentHomeBinding
import com.example.coffeeshop.helper.ManagmentCart
import com.example.coffeeshop.model.CategoryModel
import com.example.coffeeshop.model.ItemsModel
import com.example.coffeeshop.model.SizeModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var managmentCart: ManagmentCart

    private val categoryList = listOf(
        CategoryModel("Espresso", id = 1, imageResId = R.drawable.coffee1),
        CategoryModel("Latte", id = 2, imageResId = R.drawable.coffee2),
        CategoryModel("Cappuccino", id = 3, imageResId = R.drawable.coffee3),
        CategoryModel("Americano", id = 4, imageResId = R.drawable.coffee4)
    )

    private val sizeList = listOf(
        SizeModel("Small"),
        SizeModel("Medium"),
        SizeModel("Large")
    )

    private val basePopularList = listOf(
        ItemsModel(
            id = 1,
            title = "Caramel Macchiato",
            price = 4.99,
            numberInCart = 0,
            picUrl = arrayListOf("https://www.smuckers.com/smuckers/rcps/detail-pages/drinks/80254/image-thumb__80254__responsive_991_JPEG/caramel-macchiato-frappe.8b30614d.jpg")
        ),
        ItemsModel(
            id = 2,
            title = "Vanilla Latte",
            price = 5.49,
            numberInCart = 0,
            picUrl = arrayListOf("https://cdn11.bigcommerce.com/s-5ljyj9oebs/images/stencil/640w/products/6025/27336/P072023205410_1__56263.1709562034.jpg?c=2")
        ),
        ItemsModel(
            id = 3,
            title = "Iced Americano",
            price = 3.99,
            numberInCart = 0,
            picUrl = arrayListOf("https://mocktail.net/wp-content/uploads/2022/03/homemade-Iced-Americano-recipe_1.jpg")
        )
    )

    private lateinit var popularAdapter: PopularAdapter

    private val priceMultiplierMap = mapOf(
        "Small" to 1.0,
        "Medium" to 1.2,
        "Large" to 1.5
    )

    private var selectedSize = "Small"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        managmentCart = ManagmentCart(requireContext(), AppDatabase.getInstance(requireContext()))

        val categoryAdapter = CategoryAdapter(categoryList) { itemToAdd ->
            managmentCart.insertItem(itemToAdd)
            Toast.makeText(requireContext(), "${itemToAdd.title} added to cart", Toast.LENGTH_SHORT).show()
        }

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        binding.rvSizes.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = SizeAdapter(sizeList.map { it.sizeName }) { sizeSelected ->
                selectedSize = sizeSelected
                updatePricesForSize()
            }
        }

        popularAdapter = PopularAdapter(basePopularList.toMutableList()) { itemToAdd ->
            managmentCart.insertItem(itemToAdd)
            Toast.makeText(requireContext(), "${itemToAdd.title} added to cart", Toast.LENGTH_SHORT).show()
        }

        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = popularAdapter
        }
    }

    private fun updatePricesForSize() {
        val multiplier = priceMultiplierMap[selectedSize] ?: 1.0
        val updatedList = basePopularList.map { item ->
            item.copy(price = (item.price * multiplier).let { String.format("%.2f", it).toDouble() })
        }
        popularAdapter.updateItems(updatedList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
