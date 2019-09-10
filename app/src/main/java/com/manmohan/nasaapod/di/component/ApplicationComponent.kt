package com.manmohan.nasaapod.di.component

import com.manmohan.nasaapod.ApodApp
import com.manmohan.nasaapod.di.module.ActivityBindingModule
import com.manmohan.nasaapod.di.module.ApiModule
import com.manmohan.nasaapod.di.module.ApplicationModule
import com.manmohan.nasaapod.di.module.RxModule
import com.manmohan.nasaapod.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = arrayOf(
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ApiModule::class,
        RxModule::class
    )
)
interface ApplicationComponent : AndroidInjector<ApodApp> {

    override fun inject(application: ApodApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: ApodApp): Builder

        fun build(): ApplicationComponent
    }
}
