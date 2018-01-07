package me.atomx2u.rss

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import me.atomx2u.rss.ui.NavigationManager

class MainActivity : AppCompatActivity() {

    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
