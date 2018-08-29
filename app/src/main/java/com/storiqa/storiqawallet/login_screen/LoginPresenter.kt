package com.storiqa.storiqawallet.login_screen

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.storiqa.storiqawallet.constants.JsonConstants
import org.json.JSONArray
import org.json.JSONObject

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
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewState.enableSignInButton()
        } else {
            viewState.disableSignInButton()
        }
    }

    fun onSignInButtonClicked(email: String, password: String) {
        model.signInWithEmailAndPassword(email, password, {
            viewState.startMainScreen()
        }) {
            viewState.hideEmailError()
            if (it.isEmpty()) {
                viewState.showVerificationError()
            } else {
                viewState.hidePasswordError()
                viewState.hideEmailError()
                for (error in it) {
                    val detailMessageJson = JSONObject(error.data.details.message)
                    if(detailMessageJson.has(JsonConstants().email)) {
                        val errors = getErrors(detailMessageJson.getJSONArray(JsonConstants().email))
                        viewState.setEmailError(errors)
                    }

                    if(detailMessageJson.has(JsonConstants().password)) {
                        val errors = getErrors(detailMessageJson.getJSONArray(JsonConstants().password))
                        viewState.setPasswordError(errors)
                    }
                }
            }
        }
    }

    private fun getErrors(array: JSONArray) : String {
        var result = ""
        for(i in 0 until array.length()) {
            result += array.getJSONObject(i).getString(JsonConstants().message) + "\n"
        }
        return result
    }
}