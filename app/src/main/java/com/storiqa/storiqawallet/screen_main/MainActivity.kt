package com.storiqa.storiqawallet.screen_main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.db.PreferencesHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PreferencesHelper(this).setQuickLaunchFinished()
    }
}
