package com.storiqa.storiqawallet.di.modules

import androidx.appcompat.app.AppCompatActivity
import com.storiqa.storiqawallet.di.scopes.PerActivity
import com.storiqa.storiqawallet.ui.authorization.AuthorizationNavigator
import com.storiqa.storiqawallet.ui.authorization.IAuthorizationNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.base.navigator.Navigator
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.ui.main.MainNavigator
import com.storiqa.storiqawallet.ui.password.IPasswordRecoveryNavigator
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryNavigator
import com.storiqa.storiqawallet.ui.pincode.IPinCodeNavigator
import com.storiqa.storiqawallet.ui.pincode.PinCodeNavigator
import com.storiqa.storiqawallet.ui.question.IQuestionNavigator
import com.storiqa.storiqawallet.ui.question.QuestionNavigator
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
    internal fun provideAuthorizationNavigator(navigator: INavigator): IAuthorizationNavigator = AuthorizationNavigator(navigator)

    @Provides
    @PerActivity
    internal fun providePasswordRecoveryNavigator(navigator: INavigator): IPasswordRecoveryNavigator = PasswordRecoveryNavigator(navigator)

    @Provides
    @PerActivity
    internal fun providePinCodeNavigator(navigator: INavigator): IPinCodeNavigator = PinCodeNavigator(navigator)

    @Provides
    @PerActivity
    internal fun provideQuestionNavigator(navigator: INavigator): IQuestionNavigator = QuestionNavigator(navigator)

    @Provides
    @PerActivity
    internal fun provideMainNavigator(navigator: INavigator): IMainNavigator = MainNavigator(navigator)
}