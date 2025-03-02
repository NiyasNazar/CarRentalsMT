package com.pas.carrentalsmt.ui.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Html
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

import com.pas.carrentalsmt.R
import com.pas.carrentalsmt.utils.AppConstants.FONT_BOLD
import com.pas.carrentalsmt.utils.AppConstants.FONT_LIGHT
import com.pas.carrentalsmt.utils.AppConstants.FONT_NORMAL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomEditText : AppCompatEditText {



    private var fontType: Int = FONT_NORMAL
    private var text: String? = null
    private var hint: String? = null
    private var isMandatory: Boolean = false

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomEditText, 0, 0
        )
        try {
            fontType = a.getInt(R.styleable.CustomEditText_fontType, FONT_NORMAL)
            text = a.getString(R.styleable.CustomEditText_localeText)
            hint = a.getString(R.styleable.CustomEditText_localeHint)
            isMandatory = a.getBoolean(R.styleable.CustomEditText_isMandatory, false)
        } finally {
            a.recycle()
        }
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context, attrs, defStyle
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomEditText, 0, 0
        )
        try {
            fontType = a.getInt(R.styleable.CustomEditText_fontType, FONT_NORMAL)
            text = a.getString(R.styleable.CustomEditText_localeText)
            hint = a.getString(R.styleable.CustomEditText_localeHint)
        } finally {
            a.recycle()
        }
        init()
    }

    private fun init() {
        val typeface: Typeface? = when (fontType) {
            FONT_BOLD -> {
                TypeFaces.getTypeFace(context, R.font.poppins_bold)
            }

            FONT_LIGHT -> {
                TypeFaces.getTypeFace(context, R.font.poppins_regular)
            }

            else -> {
                TypeFaces.getTypeFace(context, R.font.poppins_regular)
            }
        }
        setTypeface(typeface)
        setLocaleText(text)
        setLocaleHint(hint)
        textAlignment = TEXT_ALIGNMENT_VIEW_START
        //setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom)
    }

    fun setFontType(_fontType: Int) {
        fontType = _fontType
        init()
    }

    fun setLocaleText(_text: String?) {
        text = _text
        _text?.let {
            setText(text)
        }
    }

    fun setLocaleHint(_hint: String?) {
        hint = _hint
        _hint?.let {
            val hintVal = hint
            if (isMandatory) setHint(Html.fromHtml(hintVal + "<font color=${Color.RED}> *</font>"))
            else setHint(hintVal)
            //setHint(hintVal)
        }
    }
}