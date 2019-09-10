package com.manmohan.nasaapod.ui.main

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.manmohan.nasaapod.R
import com.manmohan.nasaapod.data.api.model.Apod
import com.manmohan.nasaapod.data.api.model.Response
import com.manmohan.nasaapod.databinding.ActivityMainBinding
import com.manmohan.nasaapod.ui.apoddetail.ApodDetailActivity
import com.manmohan.nasaapod.ui.base.BaseActivity
import com.manmohan.nasaapod.ui.utils.createDatePickerApplyDialog
import com.manmohan.nasaapod.ui.utils.toStr
import java.util.*
import javax.inject.Inject
import kotlin.math.min

class MainActivity : BaseActivity<ActivityMainBinding>(), ApodListAdapter.CallBack {

    @Inject
    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var apodListAdapter: ApodListAdapter
    @Inject
    lateinit var validDate: Date

    private var startDate : Date? = null
    private var endDate : Date? = null
    private var datePickerDialog: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUserDataAdapter()
        initDatePickers()
        observeDataChange()
    }

    private fun initDatePickers() {
        startDate = validDate
        endDate = validDate
        binding?.startDate?.text = getString(R.string.start_date)
        binding?.startDate?.setOnClickListener { v -> showCalendarPicker(validDate, null,
            DatePickerDialog.OnDateSetListener{ view: DatePicker, year: Int, month: Int, day: Int ->
                startDate = Date(year - 1900, month, day)
                binding?.startDate?.text = startDate!!.toStr()
            }
        )}
        binding?.endDate?.text = getString(R.string.end_date)
        binding?.endDate?.setOnClickListener { v -> showCalendarPicker(validDate, startDate,
            DatePickerDialog.OnDateSetListener{ view: DatePicker, year: Int, month: Int, day: Int ->
                endDate = Date(year - 1900, month, day)
                binding?.endDate?.text = endDate!!.toStr()
            }
        )}
        binding?.search?.setOnClickListener { v-> mainViewModel.fetchList(startDate!!, endDate!!)}
    }

    private fun initUserDataAdapter() {
        apodListAdapter.setCallback(this)
        binding?.rvUserList?.setLayoutManager(StaggeredGridLayoutManager(2, VERTICAL))
        binding?.rvUserList?.setAdapter(apodListAdapter)
    }

    private fun observeDataChange() {
        mainViewModel.listState.observe(this, Observer {
            when (it?.currentState) {
                0 -> binding?.showLoading = true
                1 -> {
                    binding?.setShowLoading(false)
                    loadNewsData(it.data!!)
                }
                -1 -> {
                    binding?.setShowLoading(false)
                    showToast("Went something wrong")
                }
            }
        })
    }

    fun showCalendarPicker(today: Date, minDate: Date?, listener: DatePickerDialog.OnDateSetListener) {
        if (minDate!=null)
            datePickerDialog = createDatePickerApplyDialog(ContextThemeWrapper(this, R.style.AppTheme), listener, today, minDate, today)
        else
            datePickerDialog = createDatePickerApplyDialog(ContextThemeWrapper(this, R.style.AppTheme), listener, today)
        datePickerDialog!!.show()
    }

    private fun loadNewsData(data: List<Apod>) {
        apodListAdapter.addNewsList(data)
    }

    override fun onClickApod(apod: Apod) {
        ApodDetailActivity.start(this, apod)
    }

    override val layout: Int = R.layout.activity_main
}

