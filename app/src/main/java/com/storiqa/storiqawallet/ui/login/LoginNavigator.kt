package com.storiqa.storiqawallet.ui.login

import android.os.Bundle
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryActivity
import com.storiqa.storiqawallet.ui.question.QUESTION_TYPE
import com.storiqa.storiqawallet.ui.question.QuestionActivity
import com.storiqa.storiqawallet.ui.question.QuestionType
import com.storiqa.storiqawallet.ui.registration.RegistrationActivity

class LoginNavigator(private val navigator: INavigator) : ILoginNavigator {

    override fun openRegistrationActivity() {
        navigator.startActivity(RegistrationActivity::class.java)
    }

    override fun openPasswordRecoveryActivity() {
        navigator.startActivity(PasswordRecoveryActivity::class.java)
    }

    override fun openQuickLaunchQuestionActivity() {
        val bundle = Bundle()
        bundle.putSerializable(QUESTION_TYPE, QuestionType.SET_UP_QUICK_LAUNCH)
        navigator.startActivity(QuestionActivity::class.java, bundle)
    }

    override fun openEnterPinCodeActivity() {
        navigator.startActivity("com.storiqa.storiqawallet.ENTER_PIN")
    }
}