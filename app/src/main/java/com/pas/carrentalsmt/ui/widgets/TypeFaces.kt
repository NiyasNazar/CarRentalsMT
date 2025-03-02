package com.pas.carrentalsmt.ui.widgets

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import java.util.Hashtable

object TypeFaces {

    private val cache = Hashtable<String, Typeface>()

    fun getTypeFace(context: Context, assetPath: String): Typeface? {
        synchronized(cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    val typeFace = Typeface.createFromAsset(
                        context.assets, assetPath
                    )
                    cache[assetPath] = typeFace
                } catch (e: Exception) {
                    return null
                }
            }
            return cache[assetPath]
        }
    }
    fun getTypeFace(context: Context, font: Int): Typeface? {
        synchronized(cache) {
            if (!cache.containsKey(font.toString())) {
                try {
                    val typeFace =ResourcesCompat.getFont(context, font)
                    cache[font.toString()] = typeFace
                } catch (e: Exception) {
                    return null
                }
            }
            return cache[font.toString()]
        }
    }
}