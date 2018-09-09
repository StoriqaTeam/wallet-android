package com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step

import com.storiqa.storiqawallet.network.StoriqaApi
import com.storiqa.storiqawallet.network.network_requests.ResetPasswordRequest
import com.storiqa.storiqawallet.network.network_responses.ResetPasswordResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RecoverPasswordModelImp : RecoverPasswordModel {

    override fun resetPassword(email: String, sucess: (resetPasswordResponse: ResetPasswordResponse) -> Unit, failure: () -> Unit) {
            StoriqaApi.Factory().getInstance().resetPassword(ResetPasswordRequest(email))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                        sucess(it)
                    }, {
                        failure()
                    })
    }
}