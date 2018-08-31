package com.storiqa.storiqawallet.screen_recover_password.screen_recover_password_enter_second_step

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class NewPasswordEnterPresenter : MvpPresenter<NewPasswordEnterView>() {
    val model = NewPasswordEnterModelImp()

    fun onBackButtonClicked() = viewState.goBack()

    fun onEnterChanged(password: String, repeatedPassword: String) {
        if(password.isNotEmpty() && repeatedPassword.isNotEmpty()) {
            viewState.enableConfirmButton()
        } else {
            viewState.disableConfirmButton()
        }
    }

    fun onConfirmButtonClicked(password: String, repeatedPassword: String) {
//       TODO implement
    }
}