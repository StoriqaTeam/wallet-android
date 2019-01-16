package com.storiqa.storiqawallet.ui.login

interface ILoginNavigator {

    fun openRegistrationActivity()

    fun openPasswordRecoveryActivity()

    fun openQuickLaunchQuestionActivity()

    fun openEnterPinCodeActivity()

    fun closeActivity()
}