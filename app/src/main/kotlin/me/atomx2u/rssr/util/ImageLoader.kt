package me.atomx2u.rssr.util

import android.support.annotation.DrawableRes
import android.widget.ImageView

interface ImageLoader {
    fun loadImage(url: String, target: ImageView, @DrawableRes placeholder: Int, @DrawableRes errorDrawable: Int)
}