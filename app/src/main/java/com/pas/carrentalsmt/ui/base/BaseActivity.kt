package com.pas.carrentalsmt.ui.base


import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.os.Bundle

import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.lifecycle.LiveData


import androidx.lifecycle.ViewModelProvider
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.pas.carrentalsmt.R
import com.pas.carrentalsmt.ui.widgets.CustomEditText
import com.pas.carrentalsmt.utils.DialogUtils


abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {


    protected var TAG: String = this@BaseActivity.javaClass.simpleName
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    private var progressDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, getLayout())
        viewModel = ViewModelProvider(this)[getViewModelType()]

        bindViews()
    }


    abstract fun getLayout(): Int

    abstract fun getViewModelType(): Class<VM>

    abstract fun bindViews()

    abstract fun trackSessionData(): LiveData<Boolean>?

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }
    open fun showSnackBar(value: String?) {
        value?.let {
            binding?.root?.let {
                //val SNACK_BAR_MAX_LINES = 8
                val snackBar = Snackbar.make(it, value, Snackbar.LENGTH_SHORT)
                /*(snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView)
                    .run {
                        maxLines = SNACK_BAR_MAX_LINES
                    }*/

                snackBar.show()
            } ?: run {
                showToast(value)
            }
        }
    }
    open fun showToast(value: String?) {
        Toast.makeText(this, value, Toast.LENGTH_LONG).show()
    }


    open fun showBlockingProgress() {
        hideBlockingProgress()
        progressDialog = DialogUtils.showProgressDialog(this)
        progressDialog?.window?.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )
        progressDialog?.show()
    }

    open fun hideBlockingProgress() {
        if (progressDialog != null && progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

    open fun setUpToolBar(
        toolbar: Toolbar,
        title: String?,
        back: Boolean? = true,
        textColor: Int? = null
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            back?.let { hasBack -> it.setDisplayHomeAsUpEnabled(hasBack) }
            it.title = ""
        }
        val titleTextView = toolbar.findViewById<TextView>( R.id.toolbar_title)
        titleTextView.setTextColor(textColor ?: Color.WHITE)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    fun setEditTextError(editText: CustomEditText, msg: String) {
        editText.error = msg
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
    }

    fun setTextInputEditTextError(editText: TextInputLayout, msg: String) {
        editText.error = msg
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
    }


    override fun onDestroy() {

        super.onDestroy()
    }
}




