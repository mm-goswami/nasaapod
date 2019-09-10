package com.manmohan.nasaapod.ui.utils

import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.api.model.Response
import com.manmohan.nasaapod.ui.base.BaseViewState

class ListViewState private constructor(data: List<Apod>?, currentState: Int, error: Throwable?) :
    BaseViewState<List<Apod>>() {
    init {
        this.data = data
        this.error = error
        this.currentState = currentState
    }

    companion object {

        var ERROR_STATE = ListViewState(null, State.FAILED.value, Throwable())
        var LOADING_STATE = ListViewState(null, State.LOADING.value, null)
        var SUCCESS_STATE = ListViewState(ArrayList<Apod>(), State.SUCCESS.value, null)
    }


}
