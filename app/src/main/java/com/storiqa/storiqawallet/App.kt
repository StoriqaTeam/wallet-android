package com.storiqa.storiqawallet

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.content.res.Resources
import android.support.multidex.MultiDex
import com.facebook.login.LoginManager
import com.storiqa.storiqawallet.di.components.AppComponent
import com.storiqa.storiqawallet.di.components.DaggerAppComponent
import com.storiqa.storiqawallet.di.modules.AppModule
import com.storiqa.storiqawallet.di.modules.RoomModule

class App : Application(), LifecycleObserver {

    companion object {
        lateinit var instance: App
            private set

        lateinit var appComponent: AppComponent
            private set

        var density = 1f

        val res: Resources
            get() = this.instance.resources
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        density = instance.resources.displayMetrics.density
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .roomModule(RoomModule(this))
                .build()

        LoginManager.getInstance().logOut()

        MultiDex.install(this)
    }

}