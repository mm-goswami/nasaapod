package com.manmohan.nasaapod.data.api.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "apod_table")
data class Apod(
    val copyright: String?, @PrimaryKey val date: Date, val explanation: String?, val hdurl: String?, val media_type: String?,
    val service_version: String?, val title: String, val url: String, var isRead : Boolean
) : Parcelable
