package com.storiqa.storiqawallet.ui.main.wallet

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.ITokenProvider
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class WalletViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val walletApi: WalletApi,
            private val tokenProvider: ITokenProvider,
            private val signUtil: SignUtil,
            private val userData: IUserDataStorage) : BaseViewModel<IMainNavigator>() {

    lateinit var accounts: Observable<ArrayList<Bill>>

    init {
        setNavigator(navigator)

        /*val token = tokenProvider.getToken()
        if (tokenProvider.isExpired(token))
            tokenProvider.refreshToken(::requestAccounts, ::handleError)
        else
            requestAccounts(token)*/
        //todo get accounts
    }

    @SuppressLint("CheckResult")
    private fun requestAccounts(token: String) {
        val signHeader = signUtil.createSignHeader(userData.email)

        walletApi.getAccounts(userData.id, signHeader.timestamp, signHeader.deviceId, signHeader.signature, "Bearer $token", 0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    print(it)
                }, {
                    handleError(it as Exception)
                })
    }

}