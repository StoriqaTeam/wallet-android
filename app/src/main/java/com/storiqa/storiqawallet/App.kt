package com.storiqa.storiqawallet

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.content.res.Resources
import com.facebook.login.LoginManager
import com.storiqa.storiqawallet.di.components.AppComponent
import com.storiqa.storiqawallet.di.components.DaggerAppComponent
import com.storiqa.storiqawallet.di.modules.AppModule

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

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()

        instance = this
        density = instance.resources.displayMetrics.density
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        LoginManager.getInstance().logOut()
    }

}