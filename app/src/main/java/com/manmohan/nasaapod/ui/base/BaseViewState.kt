package com.manmohan.nasaapod.ui.base

open class BaseViewState<T> {

    var data: T? = null
    var error: Throwable? = null
    var currentState: Int = 0

    enum class State private constructor(var value: Int) {
        LOADING(0), SUCCESS(1), FAILED(-1)
    }
}
