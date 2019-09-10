package com.manmohan.nasaapod.data.database

import androidx.room.*
import com.manmohan.nasaapod.data.api.model.Apod
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

@Dao
interface DaoAccess {
    @Insert
    fun insertAllApod(apod: List<Apod>)

    @Insert
    fun insertApod(apod: Apod)

    @Query("SELECT * FROM apod_table")
    fun fetchAllApod(): Observable<List<Apod>>

    @Query("SELECT * FROM apod_table WHERE date BETWEEN :startDate AND :endDate")
    fun fetchAllApod(startDate: Date, endDate : Date): Observable<List<Apod>>

    @Update
    fun updateApod(apod: Apod)

    @Delete
    fun deleteApod(apod: Apod)

    @Query("SELECT * FROM apod_table WHERE date = :date")
    fun findByUserId(date: Date): Apod
}
