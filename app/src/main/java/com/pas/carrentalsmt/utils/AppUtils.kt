package com.pas.carrentalsmt.utils


import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object AppUtils  {

    fun formatDate(year: Int, month: Int, dayOfMonth: Int): Pair<String, String> {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")


        val simpleFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val isoDate = isoFormat.format(calendar.time)
        val formattedDate = simpleFormat.format(calendar.time)

        return Pair(isoDate, formattedDate)
    }
}
