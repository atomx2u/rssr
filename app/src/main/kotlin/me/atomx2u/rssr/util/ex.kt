package me.atomx2u.rssr.util

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.widget.Toast

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun Activity.defaultMetrics(): DisplayMetrics =
    DisplayMetrics().apply { windowManager.defaultDisplay.getMetrics(this@apply) }

fun Fragment.getColor(res: Int) = ContextCompat.getColor(context!!,  res)
