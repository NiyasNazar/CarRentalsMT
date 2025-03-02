package com.pas.carrentalsmt.ui.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.pas.carrentalsmt.R
import com.pas.carrentalsmt.utils.AppConstants.FONT_BOLD
import com.pas.carrentalsmt.utils.AppConstants.FONT_LIGHT
import com.pas.carrentalsmt.utils.AppConstants.FONT_NORMAL


import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomButton : AppCompatButton {



    private var fontType: Int = FONT_NORMAL
    private var text: String? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomButton, 0, 0
        )
        try {
            fontType = a.getInt(R.styleable.CustomButton_fontType, FONT_NORMAL)
            text = a.getString(R.styleable.CustomButton_localeText)
        } finally {
            a.recycle()
        }
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context, attrs, defStyle
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomButton, 0, 0
        )
        try {
            fontType = a.getInt(R.styleable.CustomButton_fontType, FONT_NORMAL)
            text = a.getString(R.styleable.CustomButton_localeText)
        } finally {
            a.recycle()
        }
        init()
    }

    private fun init() {
        val typeface: Typeface? = when (fontType) {
            FONT_BOLD -> {
                TypeFaces.getTypeFace(context, "fonts/nunito_bold.ttf")
            }

            FONT_LIGHT -> {
                TypeFaces.getTypeFace(context, "fonts/nunito_light.ttf")
            }

            else -> {
                TypeFaces.getTypeFace(context, "fonts/nunito_regular.ttf")
            }
        }
        setTypeface(typeface)
    }

    fun setFontType(_fontType: Int) {
        fontType = _fontType
        init()
    }


}