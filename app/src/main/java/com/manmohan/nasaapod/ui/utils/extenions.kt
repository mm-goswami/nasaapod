package com.manmohan.nasaapod.ui.utils

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun Date.toStr() : String {
    return String.format("%s-%s-%s", this.year + 1900, this.month + 1, this.date)
}


private val FIRST = 1
private val THIRTY_FIRST = 31
private val LAST_HOUR = 23
private val LAST_MINUTE = 59
/**
 * Only allows selection up to current day
 */
fun createDatePickerApplyDialog(
    context: Context,
    listener: DatePickerDialog.OnDateSetListener,
    date: Date
): DatePickerDialog {
    val minDate = Date(date.year, Calendar.JANUARY, 1)
    val maxDate = Date(
        date.year,
        date.month,
        date.date,
        LAST_HOUR,
        LAST_MINUTE
    )
    return createDatePickerApplyDialog(context, listener, date, minDate, maxDate)
}

fun createDatePickerApplyDialog(
    context: Context,
    listener: DatePickerDialog.OnDateSetListener,
    date: Date,
    minDate : Date,
    maxDate : Date
): DatePickerDialog {
    val datePickerDialog = DatePickerDialog(
        context, listener,
        maxDate.year,
        maxDate.month ,
        maxDate.date
    )
    datePickerDialog.datePicker.minDate = minDate.time
    datePickerDialog.datePicker.maxDate = maxDate.time // only apply up to current day
    return datePickerDialog
}