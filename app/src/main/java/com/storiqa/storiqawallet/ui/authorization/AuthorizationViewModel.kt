package com.storiqa.storiqawallet.ui.authorization

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.errors.DeviceAttachedDialogPresenter
import com.storiqa.storiqawallet.data.network.requests.ConfirmAddingDeviceRequest
import com.storiqa.storiqawallet.data.network.requests.ConfirmEmailRequest
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthorizationViewModel
@Inject
constructor(private val walletApi: WalletApi,
            private val appData: IAppDataStorage) : BaseViewModel<IAuthorizationNavigator>() {

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
                    showErrorDialog(DeviceAttachedDialogPresenter())
                }, {
                    handleError(it as Exception)
                })
    }

}