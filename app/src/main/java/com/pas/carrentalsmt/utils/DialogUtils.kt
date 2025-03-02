package com.pas.carrentalsmt.utils

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import com.pas.carrentalsmt.R


object DialogUtils {

    fun showProgressDialog(context: Context): AlertDialog? {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_blocking_progress)
        return builder.create()
    }

    fun initProgressCountDialog(
        context: Context,
        progressMax: Int
    ): ProgressDialog? {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMax(progressMax)
        progressDialog.setCancelable(false)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        return progressDialog
    }





    interface DatePickerCallback {
        fun onDateSet(year: String, month: String, day: String)
    }

}