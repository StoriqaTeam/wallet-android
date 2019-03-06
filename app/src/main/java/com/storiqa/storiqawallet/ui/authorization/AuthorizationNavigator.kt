package com.storiqa.storiqawallet.ui.authorization

import android.os.Bundle
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.ui.authorization.signin.SignInFragment
import com.storiqa.storiqawallet.ui.authorization.signup.SignUpFragment
import com.storiqa.storiqawallet.ui.base.navigator.BaseNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator
import com.storiqa.storiqawallet.ui.password.PasswordRecoveryActivity
import com.storiqa.storiqawallet.ui.question.QUESTION_TYPE
import com.storiqa.storiqawallet.ui.question.QuestionActivity
import com.storiqa.storiqawallet.ui.question.QuestionType

class AuthorizationNavigator(private val navigator: INavigator) : BaseNavigator(navigator),
        IAuthorizationNavigator {

    private val containerId = R.id.container

    override fun showSignUpFragment() {
        if (!navigator.findAndReplace(containerId, "SignUpFragment"))
            navigator.replaceFragment(containerId, SignUpFragment(), "SignUpFragment", "SignUpFragment")
    }

    override fun showSignInFragment() {
        if (!navigator.findAndReplace(containerId, "SignInFragment"))
            navigator.replaceFragment(containerId, SignInFragment(), "SignInFragment", "SignInFragment")
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

    override fun closeActivity() {
        navigator.finishActivity()
    }

}