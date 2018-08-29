package com.storiqa.storiqawallet.login_screen

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {
    private val model = LoginModelImp()


    fun onShowPasswordPressed(): Boolean {
        viewState.showPassword()
        viewState.moveInputAtTheEnd()
        return true
    }

    fun onShowPasswordButtonReleased(): Boolean {
        viewState.hidePassword()
        viewState.moveInputAtTheEnd()
        return true
    }

    fun onTextChanged(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            viewState.enableSignInButton()
        } else {
            viewState.disableSignInButton()
        }
    }

    private fun isPasswordCorrect(password: String): Boolean {
        return password.length >= 8 && password.contains("[0-9]+") && password != password.toLowerCase()
    }

    fun onSignInButtonClicked(email: String, password: String) {
        model.verifyEmail(email, {isEmailVerified ->
            if(isEmailVerified) {
                viewState.hideEmailIsNotVerifiedError()
                if(isPasswordCorrect(password)) {
                    viewState.hidePasswordError()
                    startLoginProcess(email, password)
                } else {
                    viewState.showPasswordError()
                }
            } else {
                viewState.showEmailIsNotVerifiedError()
            }
        }, {
            viewState.verificationError()
        })
    }

    private fun startLoginProcess(email: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}