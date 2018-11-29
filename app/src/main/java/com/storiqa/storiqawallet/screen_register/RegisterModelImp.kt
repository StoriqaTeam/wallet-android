package com.storiqa.storiqawallet.screen_register

import com.storiqa.storiqawallet.network.network_responses.ErrorInfo

class RegisterModelImp : RegisterModel {
    override fun registerUser(firstName: String, lastName: String, email: String, password: String, success: () -> Unit, failure: (errors: List<ErrorInfo>?) -> Unit) {
        /*StoriqaApi.Factory().getInstance().registerUser(RegisterUserRequest(email, password, firstName, lastName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    if (it.errors != null) {
                        failure(it.errors)
                    } else {
                        success()
                    }
                }, {
                    failure(null)
                })*/
    }
}