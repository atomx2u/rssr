package me.atomx2u.rssr.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageLoaderImpl(private val context: Context) : ImageLoader {

//    private val Int.drawable: Drawable get() = context.getDrawable(this)
    private val cropOptions = RequestOptions().centerCrop()

    override fun loadImage(url: String, target: ImageView, placeholder: Int, errorDrawable: Int) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().placeholder(placeholder))
            .apply(RequestOptions().error(errorDrawable))
            .apply(cropOptions)
            .into(target)
    }
}
