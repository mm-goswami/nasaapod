package com.manmohan.nasaapod.ui.apoddetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manmohan.nasaapod.data.AppDataManager
import com.manmohan.nasaapod.data.api.ApiClient
import com.manmohan.nasaapod.data.api.RxSingleSchedulers
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.api.model.Response
import com.manmohan.nasaapod.ui.utils.ListViewState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class ApodDetailViewModel @Inject
constructor(internal var dataManager: AppDataManager, internal var rxSingleSchedulers: RxSingleSchedulers) : ViewModel() {
    internal var disposable: CompositeDisposable? = null
    var listState = MutableLiveData<ListViewState>()
        internal set

    init {
        disposable = CompositeDisposable()
    }

    fun markAsRead(apod: Apod){
        dataManager.saveUserAction(apod, true)
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }
}
