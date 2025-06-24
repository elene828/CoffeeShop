package com.example.coffeeshop.helper

import android.content.Context
import android.widget.Toast
import com.example.coffeeshop.data.AppDatabase
import com.example.coffeeshop.model.ItemsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManagmentCart(
    private val context: Context,
    private val db: AppDatabase
) {
    var cartUpdateListener: CartUpdateListener? = null

    private val cartList = mutableListOf<ItemsModel>()

    init {
        loadCartFromDb()
    }

    private fun loadCartFromDb() {
        CoroutineScope(Dispatchers.IO).launch {
            val listFromDb = db.cartDao().getAll()
            synchronized(cartList) {
                cartList.clear()
                cartList.addAll(listFromDb)
            }
            CoroutineScope(Dispatchers.Main).launch {
                cartUpdateListener?.onCartUpdated(cartList.toList())
            }
        }
    }

    fun getListCart(): List<ItemsModel> = synchronized(cartList) { cartList.toList() }

    fun insertItem(item: ItemsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            synchronized(cartList) {
                val index = cartList.indexOfFirst { it.title == item.title && it.description == item.description }
                if (index >= 0) {
                    val existing = cartList[index]
                    existing.numberInCart += if (item.numberInCart > 0) item.numberInCart else 1
                    db.cartDao().update(existing)
                } else {
                    val newItem = item.copy(numberInCart = if (item.numberInCart > 0) item.numberInCart else 1)
                    db.cartDao().insert(newItem)
                    cartList.add(newItem)
                }
            }
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                cartUpdateListener?.onCartUpdated(cartList.toList())
            }
        }
    }

    fun plusItem(item: ItemsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            synchronized(cartList) {
                val index = cartList.indexOfFirst { it.title == item.title && it.description == item.description }
                if (index >= 0) {
                    val existing = cartList[index]
                    existing.numberInCart++
                    db.cartDao().update(existing)
                }
            }
            CoroutineScope(Dispatchers.Main).launch {
                cartUpdateListener?.onCartUpdated(cartList.toList())
            }
        }
    }

    fun minusItem(item: ItemsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            synchronized(cartList) {
                val index = cartList.indexOfFirst { it.title == item.title && it.description == item.description }
                if (index >= 0) {
                    val existing = cartList[index]
                    if (existing.numberInCart > 1) {
                        existing.numberInCart--
                        db.cartDao().update(existing)
                    } else {
                        db.cartDao().delete(existing)
                        cartList.removeAt(index)
                    }
                }
            }
            CoroutineScope(Dispatchers.Main).launch {
                cartUpdateListener?.onCartUpdated(cartList.toList())
            }
        }
    }

    fun getTotalFee(): Double = synchronized(cartList) {
        cartList.sumOf { it.price * it.numberInCart }
    }
}
