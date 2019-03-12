package com.storiqa.storiqawallet.di.components

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.storiqa.storiqawallet.di.modules.ActivityModule
import com.storiqa.storiqawallet.di.modules.NavigatorModule
import com.storiqa.storiqawallet.di.modules.ViewModelModule
import com.storiqa.storiqawallet.di.qualifiers.ActivityContext
import com.storiqa.storiqawallet.di.scopes.PerActivity
import com.storiqa.storiqawallet.ui.authorization.AuthorizationActivity
import com.storiqa.storiqawallet.ui.authorization.IAuthorizationNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.ui.main.MainActivity
import com.storiqa.storiqawallet.ui.password.IPasswordRecoveryNavigator
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryActivity
import com.storiqa.storiqawallet.ui.pincode.IPinCodeNavigator
import com.storiqa.storiqawallet.ui.pincode.PinCodeActivity
import com.storiqa.storiqawallet.ui.question.IQuestionNavigator
import com.storiqa.storiqawallet.ui.question.QuestionActivity
import com.storiqa.storiqawallet.ui.splash.ISplashNavigator
import com.storiqa.storiqawallet.ui.splash.SplashActivity
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class],
        modules = [ActivityModule::class, NavigatorModule::class, ViewModelModule::class])
interface ActivityComponent : ActivityComponentProvides {

    fun inject(activity: SplashActivity)
    fun inject(activity: AuthorizationActivity)
    fun inject(activity: PasswordRecoveryActivity)
    fun inject(activity: PinCodeActivity)
    fun inject(activity: QuestionActivity)
    fun inject(activity: MainActivity)

}

interface ActivityComponentProvides : AppComponentProvides {

    @ActivityContext
    fun activityContext(): Context

    fun defaultFragmentManager(): FragmentManager

    fun navigator(): INavigator
    fun splashNavigator(): ISplashNavigator
    fun authorizationNavigator(): IAuthorizationNavigator
    fun passwordRecoveryNavigator(): IPasswordRecoveryNavigator
    fun pinCodeNavigator(): IPinCodeNavigator
    fun questionNavigator(): IQuestionNavigator
    fun mainNavigator(): IMainNavigator

}