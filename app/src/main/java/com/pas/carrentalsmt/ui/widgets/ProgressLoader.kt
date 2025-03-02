package com.pas.carrentalsmt.ui.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import com.pas.carrentalsmt.R
import com.pas.carrentalsmt.utils.AppConstants.LoaderType.PRIMARY_COLOR
import com.pas.carrentalsmt.utils.AppConstants.LoaderType.WHITE


/*
class ProgressLoader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LottieAnimationView(context, attrs, defStyle) {
    private var zoomLevel: Float = 2.5f
    private var loaderType: Int = PRIMARY_COLOR
    init {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.ProgressLoader, 0, 0
        )
        loaderType = a.getInt(
            R.styleable.ProgressLoader_loaderType,
            PRIMARY_COLOR
        )
        val type = when(loaderType){
            PRIMARY_COLOR -> {
                "loader.json"
            }
            WHITE -> {
                "loader_white.json"
            }
            else -> "loader.json"
        }
        setAnimation(type)
        //playAnimation()
        repeatCount = ValueAnimator.INFINITE
        setZoomLevel()
    }

    private fun setZoomLevel() {
        scaleX = zoomLevel
        scaleY = zoomLevel
    }

    fun showProgress() {
        visibility = VISIBLE
        playAnimation()
    }

    fun hideProgress() {
        cancelAnimation()
        visibility = INVISIBLE
    }
}*/
