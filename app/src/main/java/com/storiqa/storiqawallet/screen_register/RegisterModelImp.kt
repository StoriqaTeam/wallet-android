package com.storiqa.storiqawallet.screen_register

import com.storiqa.storiqawallet.network.StoriqaApi
import com.storiqa.storiqawallet.network.network_requests.RegisterUserRequest
import com.storiqa.storiqawallet.network.network_responses.ErrorInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterModelImp : RegisterModel {
    override fun registerUser(firstName: String, lastName: String, email: String, password: String, success: () -> Unit, failure: (errors: List<ErrorInfo>?) -> Unit) {
        StoriqaApi.Factory().getInstance().registerUser(RegisterUserRequest(email, password, firstName, lastName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    if (it.errors != null) {
                        failure(it.errors)
                    } else {
                        success()
                    }
                }, {
                    failure(null)
                })
    }
}