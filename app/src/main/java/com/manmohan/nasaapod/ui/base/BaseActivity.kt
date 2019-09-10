package com.manmohan.nasaapod.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.manmohan.nasaapod.di.MyViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected var binding: B? = null

    abstract val layout: Int

    @Inject
    lateinit var myViewModelFactory: MyViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, layout)
        super.onCreate(savedInstanceState)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
