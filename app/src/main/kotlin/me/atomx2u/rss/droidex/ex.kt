package me.atomx2u.rss.droidex

import android.app.Fragment
import android.content.Context
import android.widget.Toast

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration)

fun Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this.activity, text, duration)

fun android.support.v4.app.Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this.context, text, duration)