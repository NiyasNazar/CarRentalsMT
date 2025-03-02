package com.pas.carrentalsmt

import android.app.Application
import com.pas.carrentalsmt.utils.AppConstants
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp()
class App : Application() {





    override fun onCreate() {
        super.onCreate()
        sInstance = this

    }

    companion object {
        var sInstance: App? = null
    }
}