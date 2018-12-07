package com.storiqa.storiqawallet.screen_recover_password.screen_recover_password_enter_second_step

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class NewPasswordEnterPresenter : MvpPresenter<NewPasswordEnterView>() {
    val model = NewPasswordEnterModelImp()

    fun onBackButtonClicked() = viewState.goBack()

    fun onConfirmButtonClicked(password: String, repeatedPassword: String, resetToken: String) {
        if (password.equals(repeatedPassword)) {
            viewState.showProgress()
            /*WalletApi.Factory().getInstance().applyNewPassword(ApplyNewPasswordRequest(resetToken, password)).subscribe({
                viewState.hideProgress()
                viewState.startLoginScreen()
            }, {
                viewState.hideProgress()
                viewState.showGeneralError()
            })*/
        } else {
            viewState.hideProgress()
            viewState.showPasswordsNotMatchError()
        }
    }
}