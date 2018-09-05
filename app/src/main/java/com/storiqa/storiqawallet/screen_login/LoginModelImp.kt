package com.storiqa.storiqawallet.screen_login

import com.storiqa.storiqawallet.StoriqaApp
import com.storiqa.storiqawallet.db.PreferencesHelper
import com.storiqa.storiqawallet.network.StoriqaApi
import com.storiqa.storiqawallet.network.network_requests.GetStoriqaTokenFromFirebaseTokenRequest
import com.storiqa.storiqawallet.network.network_requests.GetTokenByEmailRequest
import com.storiqa.storiqawallet.network.network_responses.ErrorInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginModelImp : LoginModel {

    override fun signInWithEmailAndPassword(email: String, password: String, success: () -> Unit, failure: (errors : List<ErrorInfo>) -> Unit) {
        StoriqaApi.Factory().getInstance().getTokenByEmailAndPassword(GetTokenByEmailRequest(email, password))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.errors != null) {
                        failure(it.errors!!)
                    } else {
                        success()
                    }
                }, {
                    failure(arrayListOf())
                })
    }

    override fun isUserFinishedQuickLaunch(): Boolean = PreferencesHelper(StoriqaApp.context).isQuickLaunchFinished()

    override fun getStoriqaToken(userToken: String, provider: String, success: (storiqaToken: String) -> Unit, failure: () -> Unit) {
        StoriqaApi.Factory().getInstance().getStoriqaTokenFromFirebaseToken(GetStoriqaTokenFromFirebaseTokenRequest(userToken, provider))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.errors != null) {
                        failure()
                    }
                    if(it.data != null) {
                        success(it.data.getJWTByProvider.token)
                    }
                }, {
                    failure()
                })
    }
}