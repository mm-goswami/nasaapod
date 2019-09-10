package com.manmohan.nasaapod.data.api


import com.manmohan.nasaapod.data.api.ApiConstants.APOD_KEY
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.api.model.Response
import io.reactivex.Observable
import retrofit2.http.Query
import javax.inject.Inject


class ApiClient @Inject
constructor(private val api: ApiEndPoint) {

    fun fetchList(startDate : String,
                  endDate : String): Observable<List<Apod>> {
        return api.fetchList(startDate, endDate, APOD_KEY)
    }

}
