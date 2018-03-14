package me.atomx2u.rssr.util

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageLoaderImpl(private val context: GlideContext) : ImageLoader {

    private val cropOptions = RequestOptions().centerCrop()

    override fun loadImage(url: String, target: ImageView, placeholder: Int, errorDrawable: Int) {
        when (context) {
            is ActivityContext -> Glide.with(context.activity)
            is FragmentContext -> Glide.with(context.fragment)
        }
            .load(url)
            .apply(RequestOptions().placeholder(placeholder))
            .apply(RequestOptions().error(errorDrawable))
            .apply(cropOptions)
            .into(target)
    }
}

sealed class GlideContext

class ActivityContext(val activity: AppCompatActivity) : GlideContext()

class FragmentContext(val fragment: Fragment) : GlideContext()
