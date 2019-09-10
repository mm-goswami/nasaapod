package com.manmohan.nasaapod.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manmohan.nasaapod.data.AppDataManager
import com.manmohan.nasaapod.data.api.ApiClient
import com.manmohan.nasaapod.data.api.RxSingleSchedulers
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.api.model.Response
import com.manmohan.nasaapod.ui.utils.ListViewState
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject


class MainViewModel @Inject
constructor(internal var dataManager: AppDataManager, internal var rxSingleSchedulers: RxSingleSchedulers) : ViewModel() {
    internal var disposable: CompositeDisposable? = null
    var listState = MutableLiveData<ListViewState>()
        internal set

    init {
        disposable = CompositeDisposable()
    }

    fun fetchList(startDate : Date, endDate : Date) {
        disposable!!.add(dataManager.getApodList(startDate, endDate)
            .doOnSubscribe { newsList -> onLoading() }
            .compose(rxSingleSchedulers.applySchedulers())
            .subscribe(
                { this.onSuccess(it) },
                { this.onError(it) })
        )
    }

    private fun onSuccess(list: List<Apod>) {
        ListViewState.SUCCESS_STATE.data = list
        listState.postValue(ListViewState.SUCCESS_STATE)
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
        Log.e("MainViewModel", error.toString())
        ListViewState.ERROR_STATE.error = error
        listState.postValue(ListViewState.ERROR_STATE)
    }

    private fun onLoading() {
        listState.postValue(ListViewState.LOADING_STATE)
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }
}
