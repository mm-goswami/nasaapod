package com.manmohan.nasaapod.data.api

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSingleSchedulers {

    fun <T> applySchedulers(): ObservableTransformer<T, T>

    companion object {
        val DEFAULT: RxSingleSchedulers = object : RxSingleSchedulers {
            override fun <T> applySchedulers(): ObservableTransformer<T, T> {
                return ObservableTransformer {
                    it
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                }
            }
        }

        val TEST_SCHEDULER: RxSingleSchedulers = object : RxSingleSchedulers {
            override fun <T> applySchedulers(): ObservableTransformer<T, T> {
                return ObservableTransformer { single ->
                    single
                        .subscribeOn(Schedulers.trampoline())
                        .observeOn(Schedulers.trampoline())
                }
            }
        }
    }
}
