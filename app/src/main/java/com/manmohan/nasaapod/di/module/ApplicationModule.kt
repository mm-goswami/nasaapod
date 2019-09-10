package com.manmohan.nasaapod.di.module

import android.app.Application
import android.content.Context

import dagger.Binds
import dagger.Module


@Module(includes = arrayOf(ViewModelModule::class))
abstract class ApplicationModule {

    @Binds
    internal abstract fun provideContext(application: Application): Context

}
