package com.storiqa.storiqawallet.ui.authorization

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.DeviceAttachedDialogPresenter
import com.storiqa.storiqawallet.network.requests.ConfirmAddingDeviceRequest
import com.storiqa.storiqawallet.network.requests.ConfirmEmailRequest
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthorizationViewModel
@Inject
constructor(navigator: IAuthorizationNavigator,
            private val walletApi: WalletApi,
            private val appData: IAppDataStorage) : BaseViewModel<IAuthorizationNavigator>() {

    init {
        setNavigator(navigator)
    }

    fun onSignInTabSelected() {
        getNavigator()?.showSignInFragment()
    }

    fun onSignUpTabSelected() {
        getNavigator()?.showSignUpFragment()
    }

    @SuppressLint("CheckResult")
    fun confirmEmail(token: String) {
        showLoadingDialog()

        walletApi
                .confirmEmail(ConfirmEmailRequest(token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoadingDialog()
                }, {
                    handleError(it as Exception)
                })
    }

    @SuppressLint("CheckResult")
    fun confirmAttachDevice(token: String) {
        showLoadingDialog()

        walletApi
                .confirmAddingDevice(ConfirmAddingDeviceRequest(token, appData.deviceId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoadingDialog()
                    showMessageDialog(DeviceAttachedDialogPresenter())
                }, {
                    handleError(it as Exception)
                })
    }

}