package com.example.coffeeshop.helper

import com.example.coffeeshop.model.ItemsModel

interface CartUpdateListener {
    fun onCartUpdated(updatedCart: List<ItemsModel>)
}
