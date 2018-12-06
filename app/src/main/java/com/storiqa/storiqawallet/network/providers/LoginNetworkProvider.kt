package com.storiqa.storiqawallet.network.providers

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.storiqa.storiqawallet.common.IError
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.common.RequestHeaders
import com.storiqa.storiqawallet.network.requests.LoginRequest
import com.storiqa.storiqawallet.network.responses.LoginErrorResponse
import com.storiqa.storiqawallet.network.responses.TokenResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

const val CODE_EMAIL_NOT_EXIST = "Email not found"
const val CODE_EMAIL_NOT_VALID = "Invalid email format"
const val CODE_PASS_WRONG = "password"
const val CODE_DEVICE_NOT_EXIST = "exists"

interface ILoginNetworkProvider {

    fun requestLogIn(
            headers: RequestHeaders,
            request: LoginRequest,
            onSuccess: (it: TokenResponse?) -> Unit,
            onFailure: (it: IError) -> Unit)

}

class LoginNetworkProvider
@Inject
constructor(private val walletApi: WalletApi) : ILoginNetworkProvider {

    private lateinit var onSuccessCallback: (it: TokenResponse?) -> Unit
    private lateinit var onFailureCallback: (it: IError) -> Unit

    @SuppressLint("CheckResult")
    override fun requestLogIn(
            headers: RequestHeaders,
            request: LoginRequest,
            onSuccess: (it: TokenResponse?) -> Unit,
            onFailure: (it: IError) -> Unit) {

        onSuccessCallback = onSuccess
        onFailureCallback = onFailure

        walletApi
                .login(headers.timestamp, headers.deviceId, headers.sign, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ handleSuccessResponse(it) }, { handleFailureResponse(it) })
        //.doOnNext { handleSuccessResponse(it) }
        //.doOnError { handleFailureResponse(it) }
        //.subscribe()
    }

    private fun handleSuccessResponse(response: Response<TokenResponse>) {
        when (response.code()) {
            200 -> onSuccessCallback(response.body())
            422 -> {
                val gson = Gson()
                val loginErrorResponse = gson.fromJson(response.errorBody()?.string(),
                        LoginErrorResponse::class.java)

                val emailErrorCode = loginErrorResponse.email?.get(0)?.message
                when {
                    emailErrorCode.equals(CODE_EMAIL_NOT_VALID) ->
                        onFailureCallback(LoginError.EMAIL_NOT_VALID)

                    emailErrorCode.equals(CODE_EMAIL_NOT_EXIST) ->
                        onFailureCallback(LoginError.EMAIL_NOT_EXIST)
                }

                val passwordErrorCode = loginErrorResponse.password?.get(0)?.code
                if (passwordErrorCode.equals(CODE_PASS_WRONG))
                    onFailureCallback(LoginError.PASS_WRONG)

                if (loginErrorResponse.device?.get(0)?.code.equals(CODE_DEVICE_NOT_EXIST))
                    onFailureCallback(LoginError.DEVICE_NOT_EXIST)
            }
            500 -> {
                onFailureCallback(LoginError.SERVER_ERROR)
            }
        }
    }

    private fun handleFailureResponse(throwable: Throwable) {
        throwable.printStackTrace()
        if (throwable is IOException)
            onFailureCallback(LoginError.NO_INTERNET)
        else //converter errors
            onFailureCallback(LoginError.UNKNOWN_ERROR)
    }

}

enum class LoginError : IError {
    EMAIL_NOT_VALID, EMAIL_NOT_EXIST, PASS_WRONG, DEVICE_NOT_EXIST,
    SERVER_ERROR, NO_INTERNET, UNKNOWN_ERROR
}


