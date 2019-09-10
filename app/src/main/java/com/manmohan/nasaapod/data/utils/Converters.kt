package com.manmohan.nasaapod.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class Converters {

    @TypeConverter
    fun fromString(value: String): Date {
        val listType = object : TypeToken<Date>() {

        }.type
        return Gson().fromJson<Date>(value, listType)
    }

    @TypeConverter
    fun fromDate(date: Date): String {
        val gson = Gson()
        return gson.toJson(date)
    }
}