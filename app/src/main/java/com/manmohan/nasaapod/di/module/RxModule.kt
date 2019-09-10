package com.manmohan.nasaapod.di.module


import com.manmohan.nasaapod.data.api.RxSingleSchedulers
import com.manmohan.nasaapod.di.scope.AppScope
import dagger.Module
import dagger.Provides


@Module
class RxModule {
    @AppScope
    @Provides
    fun providesScheduler(): RxSingleSchedulers {
        return RxSingleSchedulers.DEFAULT
    }
}
