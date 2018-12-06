package com.storiqa.storiqawallet.di.modules

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.di.qualifiers.ActivityContext
import com.storiqa.storiqawallet.di.scopes.PerActivity
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.base.navigator.Navigator
import com.storiqa.storiqawallet.ui.login.ILoginNavigator
import com.storiqa.storiqawallet.ui.login.LoginNavigator
import com.storiqa.storiqawallet.ui.splash.ISplashNavigator
import com.storiqa.storiqawallet.ui.splash.SplashNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    @ActivityContext
    internal fun provideActivityContext(): Context = activity

    @Provides
    @PerActivity
    internal fun provideFragmentManager(): FragmentManager = activity.supportFragmentManager

    @Provides
    @PerActivity
    internal fun provideNavigator(): INavigator = Navigator(activity)

    @Provides
    @PerActivity
    internal fun provideSplashNavigator(): ISplashNavigator = SplashNavigator(activity)

    @Provides
    @PerActivity
    internal fun provideLoginNavigator(): ILoginNavigator = LoginNavigator(activity)

}