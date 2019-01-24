package com.storiqa.storiqawallet.ui.authorization

import com.storiqa.storiqawallet.ui.base.navigator.IBaseNavigator

interface IAuthorizationNavigator : IBaseNavigator {

    fun showSignUpFragment()

    fun showSignInFragment()

    fun openPasswordRecoveryActivity()

    fun openQuickLaunchQuestionActivity()

    fun openEnterPinCodeActivity()

    fun closeActivity()

}