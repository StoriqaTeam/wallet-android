package com.storiqa.storiqawallet

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Context
import android.support.multidex.MultiDex
import com.storiqa.storiqawallet.di.apiModule
import com.storiqa.storiqawallet.di.viewModelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule

class App : Application(), LifecycleObserver, KodeinAware {

    override val kodein = Kodein.lazy {
        import(apiModule)
        import(viewModelModule)
        import(androidModule(this@App))
    }

    companion object {
        lateinit var context: Context

        fun getStringFromResources(id: Int): String {
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

}