package com.storiqa.storiqawallet

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.content.res.Resources
import android.support.multidex.MultiDex
import com.storiqa.storiqawallet.di.components.AppComponent
import com.storiqa.storiqawallet.di.components.DaggerAppComponent
import com.storiqa.storiqawallet.di.modules.AppModule

class App : Application(), LifecycleObserver {

    companion object {
        lateinit var instance: App
            private set

        lateinit var appComponent: AppComponent
            private set

        val res: Resources
            get() = this.instance.resources

        fun getStringFromResources(id: Int): String {
            return this.instance.getString(id)
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}