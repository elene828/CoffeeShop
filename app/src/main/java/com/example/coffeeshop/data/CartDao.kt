package com.example.coffeeshop.room

import androidx.room.*
import com.example.coffeeshop.model.ItemsModel

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ItemsModel)

    @Update
    fun update(item: ItemsModel)

    @Delete
    fun delete(item: ItemsModel)

    @Query("SELECT * FROM cart_items")
    fun getAll(): List<ItemsModel>
}
