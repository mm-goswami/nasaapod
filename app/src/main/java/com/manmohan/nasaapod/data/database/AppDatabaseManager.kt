package com.manmohan.nasaapod.data.database

import android.util.Log
import androidx.room.Room
import com.manmohan.nasaapod.ApodApp
import com.manmohan.nasaapod.data.api.RxSingleSchedulers
import com.manmohan.nasaapod.data.api.model.Apod
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.*
import javax.inject.Inject

/**
 * Created by manmohan.
 */
class AppDatabaseManager @Inject
constructor(context: ApodApp) {

    private val apodDatabase: ApodDatabase

    val allApod: Observable<List<Apod>>
        get() = apodDatabase.daoAccess().fetchAllApod().compose(applyGlobalTransformer())

    init {
        apodDatabase = Room.databaseBuilder(context, ApodDatabase::class.java, DB_NAME).build()
    }

    fun getApodList(startDate : Date, endDate : Date) : Observable<List<Apod>>{
        return apodDatabase.daoAccess().fetchAllApod(startDate, endDate).compose(applyGlobalTransformer())
    }

    fun saveUserAction(apod: Apod, isRead: Boolean) {
        Completable.fromAction {
            apod.isRead = isRead
            apodDatabase.daoAccess().updateApod(apod)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    Log.d(TAG, "Apod update success: " + apod.copyright)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.e(TAG, "Apod update failed: " + apod.copyright)
                }
            })
    }

    fun saveUser(apods: List<Apod>) {
        Completable.fromAction {
            for (apod in apods) {
                if (apodDatabase.daoAccess().findByUserId(apod.date)!=null){
                    apodDatabase.daoAccess().updateApod(apod)
                } else {
                    try {
                        apodDatabase.daoAccess().insertApod(apod)
                    } catch (ex: Exception) {
                    }
                }
            }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    Log.d(TAG, "User list saved")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.e(TAG, "User list saved failed: " + e.message)
                }
            })
    }

    fun isUserReadedApod(entity: Apod): Boolean? {
        return apodDatabase.daoAccess().findByUserId(entity.date)?.isRead
    }

    /**
     * Performs global operators on all rx chains that compose with this transformer
     */
    private fun <T> applyGlobalTransformer(): ObservableTransformer<T, T> {
        return RxSingleSchedulers.DEFAULT.applySchedulers()
    }

    companion object {

        val DB_NAME = "apod_db"
        private val TAG = "AppDatabaseManager"
    }
}
