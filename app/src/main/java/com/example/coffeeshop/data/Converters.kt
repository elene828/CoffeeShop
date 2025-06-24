package com.example.coffeeshop.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: ArrayList<String>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): ArrayList<String> {
        if (value.isNullOrEmpty()) return arrayListOf()
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}
