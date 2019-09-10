package com.manmohan.nasaapod.di.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manmohan.nasaapod.di.MyViewModelFactory
import com.manmohan.nasaapod.di.scope.ViewModelKey
import com.manmohan.nasaapod.ui.apoddetail.ApodDetailViewModel
import com.manmohan.nasaapod.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ApodDetailViewModel::class)
    internal abstract fun bindApodDetailViewModel(mainViewModel: ApodDetailViewModel): ViewModel


    @Binds
    internal abstract fun bindMyViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory
}
