package com.storiqa.storiqawallet.ui.pincode

import android.databinding.ObservableBoolean
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import javax.inject.Inject

class PinCodeViewModel
@Inject
constructor(navigator: IPinCodeNavigator,
            private val walletApi: WalletApi,
            private val userData: IUserDataStorage,
            private val appData: IAppDataStorage) :
        BaseViewModel<IPinCodeNavigator>() {

    private val pinLength = App.res.getInteger(R.integer.PIN_LENGTH)

    var enteredPinCode: String = ""
    val pinCode = NonNullObservableField("")
    val title = NonNullObservableField("")
    val description = NonNullObservableField("")
    val forgotPinVisible = ObservableBoolean(false)

    val showPinError = SingleLiveEvent<String>()

    var state: PinCodeState = PinCodeState.ENTER
        set(value) {
            field = value
            pinCode.set("")
            when (value) {
                PinCodeState.SET_UP -> {
                    title.set(App.res.getString(R.string.text_pin_title_setup))
                    description.set(App.res.getString(R.string.text_pin_description_setup))
                }
                PinCodeState.CONFIRM -> {
                    title.set(App.res.getString(R.string.text_pin_title_confirm))
                    description.set(App.res.getString(R.string.text_pin_description_confirm))
                }
                PinCodeState.ENTER -> {
                    title.set(App.res.getString(R.string.text_pin_title_enter, userData.firstName))
                    description.set(App.res.getString(R.string.text_pin_description_enter))
                    forgotPinVisible.set(true)
                }
            }
        }

    init {
        setNavigator(navigator)

        pinCode.addOnPropertyChanged {
            if (pinCode.get().length == pinLength)
                onPinCodeEntered()
        }
    }

    fun onDigitEntered(digit: Int) {
        if (pinCode.get().length == pinLength)
            return

        pinCode.set(pinCode.get() + digit)
    }

    fun deleteLastDigit() {
        pinCode.set(pinCode.get().dropLast(1))
    }

    fun onForgotPinButtonClicked() {

    }

    private fun onPinCodeEntered() {
        when (state) {
            PinCodeState.SET_UP -> {
                enteredPinCode = pinCode.get()
                state = PinCodeState.CONFIRM
            }
            PinCodeState.CONFIRM -> {
                if (enteredPinCode == pinCode.get()) {
                    appData.pin = enteredPinCode
                    appData.isPinEntered = true
                    getNavigator()?.openMainActivity()
                } else {
                    showPinError.trigger()
                    pinCode.set("")
                }
            }
            PinCodeState.ENTER -> {
                if (isValidPinCode(pinCode.get()))
                    getNavigator()?.openMainActivity()
                else {
                    showPinError.trigger()
                    pinCode.set("")
                }
            }
        }
    }

    private fun isValidPinCode(pinCode: String): Boolean {
        return pinCode == appData.pin
    }

    enum class PinCodeState {
        SET_UP, CONFIRM, ENTER
    }
}