package com.storiqa.storiqawallet

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

class StoriqaApp : Application() {

    companion object {
       lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    protected override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}