package com.storiqa.storiqawallet.data

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.utils.JWTUtil
import com.storiqa.storiqawallet.utils.SignUtil
import com.storiqa.storiqawallet.utils.getCurrentTimeMillis
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TokenProvider(private val appData: IAppDataStorage,
                    private val signUtil: SignUtil,
                    private val walletApi: WalletApi) : ITokenProvider {

    override fun getToken(): String {
        return appData.token
    }

    override fun isExpired(token: String): Boolean {
        return JWTUtil.getExpiredTime(token) + 30000 < getCurrentTimeMillis()
    }

    @SuppressLint("CheckResult")
    override fun refreshToken(f: (String) -> Unit, errorHandler: (Exception) -> Unit) {
        val signHeader = signUtil.createSignHeader(appData.currentUserEmail)

        walletApi
                .refreshToken(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer ${getToken()}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    appData.token = it
                    f(it)
                }, {
                    errorHandler(it as Exception)
                })
    }

    @SuppressLint("CheckResult")
    override fun revokeToken(errorHandler: (Exception) -> Unit) {
        //todo appData.token = -1
        val signHeader = signUtil.createSignHeader(appData.currentUserEmail)

        walletApi
                .revokeToken(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer ${getToken()}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    errorHandler(it as Exception)
                })
    }
}