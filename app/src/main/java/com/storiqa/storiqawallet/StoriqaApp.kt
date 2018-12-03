package com.storiqa.storiqawallet

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Context
import android.support.multidex.MultiDex

class StoriqaApp : Application(), LifecycleObserver {

    private var isQuited = false

    companion object {
        lateinit var context: Context

        fun getStringFromRecources(id: Int): String {
            return context.getString(id)
        }
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

/*
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
    }*/
}