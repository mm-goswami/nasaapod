package com.manmohan.nasaapod.di.module


import androidx.lifecycle.ViewModelProviders
import com.manmohan.nasaapod.ui.apoddetail.ApodDetailActivity
import com.manmohan.nasaapod.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
abstract class ApodDetailActivityModule() {

    abstract fun providesContext(activity: ApodDetailActivity): ApodDetailActivity

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideManinViewModel(activity: ApodDetailActivity): MainViewModel {
            return ViewModelProviders.of(activity, activity.myViewModelFactory).get(MainViewModel::class.java)
        }
    }
}
