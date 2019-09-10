package com.manmohan.nasaapod.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.utils.Converters

@Database(entities = arrayOf(Apod::class), version = 1)
@TypeConverters(Converters::class)
abstract class ApodDatabase : RoomDatabase() {

    abstract fun daoAccess(): DaoAccess

}