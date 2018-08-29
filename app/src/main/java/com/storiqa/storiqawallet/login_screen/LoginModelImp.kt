package com.storiqa.storiqawallet.login_screen

import com.storiqa.storiqawallet.network.StoriqaApi
import com.storiqa.storiqawallet.network.network_requests.GetTokenByEmailRequest
import com.storiqa.storiqawallet.network.network_responses.GetTokenError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginModelImp : LoginModel {
    override fun verifyEmail(email: String, onVerified: (result: Boolean) -> Unit, failure: () -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signInWithEmailAndPassword(email: String, password: String, success: () -> Unit, failure: (errors : List<GetTokenError>) -> Unit) {
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
}