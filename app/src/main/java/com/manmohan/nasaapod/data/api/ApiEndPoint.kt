package com.manmohan.nasaapod.data.api

import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.api.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoint {

    @GET(ApiConstants.APOD_URL)
    fun fetchList(@Query("start_date") startDate : String,
                  @Query("end_date") endDate : String,
                  @Query("api_key") apiKey : String): Observable<List<Apod>>
}
