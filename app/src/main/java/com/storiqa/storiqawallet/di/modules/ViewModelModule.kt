package com.storiqa.storiqawallet.di.modules

import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel
    //internal abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): LoginViewModel

}