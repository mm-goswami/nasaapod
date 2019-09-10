package com.manmohan.nasaapod.data

import android.net.ConnectivityManager
import com.manmohan.nasaapod.data.api.ApiClient
import com.manmohan.nasaapod.data.api.RxSingleSchedulers
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.database.AppDatabaseManager
import com.manmohan.nasaapod.ui.utils.toStr
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by manmohan.
 */
class AppDataManager @Inject
constructor(
    private val mApiManager: ApiClient,
    private val mDatabaseManager: AppDatabaseManager,
    private val mConnectivityManager: ConnectivityManager
) {

    fun getApodList(startDate : Date, endDate : Date) : Observable<List<Apod>> {
        return if (!isNetworkConnected)
            mDatabaseManager.getApodList(startDate, endDate)
                .flatMapIterable { it}
                .sorted { o1, o2 -> compareDate(o1, o2)}
                .toList()
                .toObservable()
        else
            mApiManager.fetchList(startDate.toStr(), endDate.toStr())
                .flatMapIterable { it}
                .sorted { o1, o2 -> compareDate(o1, o2)}
                .map {
                    val isRead = mDatabaseManager.isUserReadedApod(it)
                    if(isRead != null)
                        it.isRead = isRead
                    else
                        it.isRead = false
                    it
                }
                .toList()
                .toObservable()
                .doOnNext {item -> mDatabaseManager.saveUser(item)}
    }

    private fun compareDate(o1: Apod?, o2: Apod?): Int {
        return o2?.date?.compareTo(o1?.date)!!
    }


    val isNetworkConnected: Boolean
        get() {
            val activeNetwork = mConnectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    fun saveUserAction(apod: Apod, isRead: Boolean) {
        mDatabaseManager.saveUserAction(apod, isRead)
    }

    /**
     * Performs global operators on all rx chains that compose with this transformer
     */
    private fun <T> applyGlobalTransformer(): ObservableTransformer<T, T> {
        return RxSingleSchedulers.DEFAULT.applySchedulers()
    }
}

