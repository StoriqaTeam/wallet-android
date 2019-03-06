package com.storiqa.storiqawallet.provider

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.network.OpenWalletApi
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.utils.SignUtil

class TokenProvider(
        private val appData: IAppDataStorage,
        private val openWalletApi: OpenWalletApi,
        private val signUtil: SignUtil) : ITokenProvider {

    override var accessToken = ""
        private set
        get() = appData.token

    override fun refreshToken(): String? {
        val signHeader = signUtil.createSignHeader(appData.currentUserEmail)
        val token = openWalletApi
                .refreshToken(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $accessToken")
                .execute()
                .body()
        token?.let {
            accessToken = it
            appData.token = it
        }
        return token
    }

    @SuppressLint("CheckResult")
    override fun revokeToken(errorHandler: (Exception) -> Unit) {
        //todo appData.token = -1
        /*val signHeader = signUtil.createSignHeader(appData.currentUserEmail)

        walletApi
                .revokeToken(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer ${getAccessToken()}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    errorHandler(it as Exception)
                })*/
    }
}