package com.manmohan.nasaapod.di.module

import androidx.databinding.ViewDataBinding
import com.manmohan.nasaapod.ui.apoddetail.ApodDetailActivity
import com.manmohan.nasaapod.ui.base.BaseActivity
import com.manmohan.nasaapod.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @Module
    internal interface InnerActivityModule {
        fun bindMainActivity(activity: BaseActivity<ViewDataBinding>): BaseActivity<ViewDataBinding>
    }

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, InnerActivityModule::class))
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(ApodDetailActivityModule::class, InnerActivityModule::class))
    internal abstract fun apodDetailActivity(): ApodDetailActivity

}
