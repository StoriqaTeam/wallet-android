package com.storiqa.storiqawallet.register_screen

import com.storiqa.storiqawallet.network.StoriqaApi
import com.storiqa.storiqawallet.network.network_requests.RegisterUserRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterModelImp : RegisterModel {
    override fun registerUser(firstName: String, lastName: String, email: String, password: String, success: () -> Unit, failure: () -> Unit) {
        StoriqaApi.Factory().getInstance().registerUser(RegisterUserRequest(email, password, firstName, lastName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    if(it.errors != null) {
                        failure()
                    } else {
                        success()
                    }
                }, {
                    failure()
                })
    }
}