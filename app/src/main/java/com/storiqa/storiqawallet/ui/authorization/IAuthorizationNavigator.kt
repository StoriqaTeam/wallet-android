package com.storiqa.storiqawallet.ui.authorization

interface IAuthorizationNavigator {

    fun showSignUpFragment()

    fun showSignInFragment()

    fun openPasswordRecoveryActivity()

    fun openQuickLaunchQuestionActivity()

    fun openEnterPinCodeActivity()

    fun closeActivity()

}