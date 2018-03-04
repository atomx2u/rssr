package me.atomx2u.rss.droidex

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.text.BoringLayout
import android.util.DisplayMetrics
import android.widget.Toast

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this.activity, text, duration).show()

fun android.support.v4.app.Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this.context, text, duration).show()

fun Activity.defaultMetrics(): DisplayMetrics =
    DisplayMetrics().apply { windowManager.defaultDisplay.getMetrics(this@apply) }