package com.storiqa.storiqawallet.data

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.network.OpenWalletApi
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.utils.SignUtil

class TokenProvider(
        private val appData: IAppDataStorage,
        private val openWalletApi: OpenWalletApi,
        private val signUtil: SignUtil) : ITokenProvider {

    override fun getAccessToken(): String {
        return appData.token
    }

    /*@SuppressLint("CheckResult")
    override fun refreshToken(f: (String) -> Unit, errorHandler: (Exception) -> Unit) {
        val signHeader = signUtil.createSignHeader(appData.currentUserEmail)

        walletApi
                .refreshToken(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer ${getAccessToken()}")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    appData.token = it
                    f(it)
                }, {
                    errorHandler(it as Exception)
                })
    }*/

    override fun refreshToken(): String? {
        val signHeader = signUtil.createSignHeader(appData.currentUserEmail)
        val token = openWalletApi
                .refreshToken(signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer ${getAccessToken()}")
                .execute()
                .body()
        token?.let { appData.token = it }
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