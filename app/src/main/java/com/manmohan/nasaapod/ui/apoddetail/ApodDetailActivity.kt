package com.manmohan.nasaapod.ui.apoddetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.manmohan.nasaapod.R
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.api.model.Response
import com.manmohan.nasaapod.databinding.ActivityApodDetailBinding
import com.manmohan.nasaapod.ui.base.BaseActivity
import com.manmohan.nasaapod.ui.utils.toStr
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

class ApodDetailActivity : BaseActivity<ActivityApodDetailBinding>() {

    companion object {

        val APOD_EXTRA : String = "apod_extra"

        fun start(activity: Activity, apod: Apod){
            var intent : Intent = Intent(activity, ApodDetailActivity::class.java)
            intent.putExtra(APOD_EXTRA, apod);
            activity.startActivity(intent)
        }
    }

    @Inject
    lateinit var apodDetailViewModel: ApodDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initApodData()
        observeDataChange()
    }

    private fun initApodData() {
        val apod: Apod = intent.getParcelableExtra(APOD_EXTRA)
        binding?.apod = apod
        apodDetailViewModel.markAsRead(apod)
        binding?.date?.text = apod.date.toStr()
    }

    private fun observeDataChange() {
    }


    override val layout: Int = R.layout.activity_apod_detail
}

