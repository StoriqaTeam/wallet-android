package com.storiqa.storiqawallet

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

class StoriqaApp : Application() {

    protected override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}