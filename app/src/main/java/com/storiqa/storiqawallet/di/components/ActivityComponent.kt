package com.storiqa.storiqawallet.di.components

import android.content.Context
import android.support.v4.app.FragmentManager
import com.storiqa.storiqawallet.di.modules.ActivityModule
import com.storiqa.storiqawallet.di.modules.NavigatorModule
import com.storiqa.storiqawallet.di.modules.ViewModelModule
import com.storiqa.storiqawallet.di.qualifiers.ActivityContext
import com.storiqa.storiqawallet.di.scopes.PerActivity
import com.storiqa.storiqawallet.network.errors.ErrorHandler
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.login.ILoginNavigator
import com.storiqa.storiqawallet.ui.login.LoginActivity
import com.storiqa.storiqawallet.ui.splash.ISplashNavigator
import com.storiqa.storiqawallet.ui.splash.SplashActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(ActivityModule::class, NavigatorModule::class, ViewModelModule::class))
interface ActivityComponent : ActivityComponentProvides {

    fun inject(activity: SplashActivity)
    fun inject(activity: LoginActivity)

}

interface ActivityComponentProvides : AppComponentProvides {

    @ActivityContext
    fun activityContext(): Context
    fun defaultFragmentManager(): FragmentManager
    fun errorHandler(): ErrorHandler

    fun navigator(): INavigator
    fun splashNavigator(): ISplashNavigator
    fun loginNavigator(): ILoginNavigator

}