package com.storiqa.storiqawallet

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Context
import android.support.multidex.MultiDex
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.objects.ScreenStarter

class StoriqaApp : Application(), LifecycleObserver {

    private var isQuited = false

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded1() {
        isQuited = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        if (isQuited) {
            if (PreferencesHelper(applicationContext).isPinCodeEnabled()) {
                ScreenStarter().startEnterPinCodeScreenForLogin(applicationContext)
            } else {
                ScreenStarter().startLoginScreen(applicationContext)
            }
            isQuited = false
        }
    }
}