package com.manmohan.nasaapod.di.module


import androidx.lifecycle.ViewModelProviders
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.ui.main.ApodListAdapter
import com.manmohan.nasaapod.ui.main.MainActivity
import com.manmohan.nasaapod.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
abstract class MainActivityModule {

    abstract fun providesContext(activity: MainActivity): MainActivity

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideApodListAdapter(activity: MainActivity): ApodListAdapter {
            return ApodListAdapter(activity, ArrayList<Apod>())
        }

        @JvmStatic
        @Provides
        fun provideManinViewModel(activity: MainActivity): MainViewModel {
            return ViewModelProviders.of(activity, activity.myViewModelFactory).get(MainViewModel::class.java)
        }
    }
}
