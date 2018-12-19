package com.storiqa.storiqawallet.di.modules

import android.support.v7.app.AppCompatActivity
import com.storiqa.storiqawallet.di.scopes.PerActivity
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.base.navigator.Navigator
import com.storiqa.storiqawallet.ui.login.ILoginNavigator
import com.storiqa.storiqawallet.ui.login.LoginNavigator
import com.storiqa.storiqawallet.ui.password.IPasswordRecoveryNavigator
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryNavigator
import com.storiqa.storiqawallet.ui.registration.IRegistrationNavigator
import com.storiqa.storiqawallet.ui.registration.RegistrationNavigator
import com.storiqa.storiqawallet.ui.splash.ISplashNavigator
import com.storiqa.storiqawallet.ui.splash.SplashNavigator
import dagger.Module
import dagger.Provides

@Module
class NavigatorModule(private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    internal fun provideNavigator(): INavigator = Navigator(activity)

    @Provides
    @PerActivity
    internal fun provideSplashNavigator(navigator: INavigator): ISplashNavigator = SplashNavigator(navigator)

    @Provides
    @PerActivity
    internal fun provideLoginNavigator(navigator: INavigator): ILoginNavigator = LoginNavigator(navigator)

    @Provides
    @PerActivity
    internal fun providePasswordRecoveryNavigator(navigator: INavigator): IPasswordRecoveryNavigator = PasswordRecoveryNavigator(navigator)

    @Provides
    @PerActivity
    internal fun provideRegistrationNavigator(navigator: INavigator): IRegistrationNavigator = RegistrationNavigator(navigator)

}