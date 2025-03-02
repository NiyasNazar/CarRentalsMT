package com.acube.itms.utils.date_picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerDialogFragment (
    private val requestCode: Int,

    private val listener: DatePickerListener
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    interface DatePickerListener {
        fun onDateSelected(requestCode: Int,year: Int, month: Int, dayOfMonth: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)

        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        listener.onDateSelected(requestCode,year, month, dayOfMonth)
    }
}
