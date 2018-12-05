package com.storiqa.storiqawallet.screen_login

import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.network.network_responses.ErrorInfo

class LoginModelImp : LoginModel {

    override fun signInWithEmailAndPassword(email: String, password: String, success: () -> Unit, failure: (errors: List<ErrorInfo>) -> Unit) {
        /*WalletApi.Factory().getInstance().getTokenByEmailAndPassword(GetTokenByEmailRequest(email, password))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.errors != null) {
                        failure(it.errors)
                    } else {
                        success()
                    }
                }, {
                    failure(arrayListOf())
                })*/
    }

    override fun isPinCodeEntered(): Boolean {
        return PreferencesHelper(App.context).isPinCodeEnabled()
    }

    override fun isUserFinishedQuickLaunch(): Boolean = PreferencesHelper(App.context).isQuickLaunchFinished()

    override fun getStoriqaToken(userToken: String, provider: String, success: (storiqaToken: String) -> Unit, failure: () -> Unit) {
        /*WalletApi.Factory().getInstance().getStoriqaTokenFromFirebaseToken(GetStoriqaTokenFromFirebaseTokenRequest(userToken, provider))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.errors != null) {
                        failure()
                    }
                    if (it.data != null) {
                        success(it.data.getJWTByProvider.token)
                    }
                }, {
                    failure()
                })*/
    }
}