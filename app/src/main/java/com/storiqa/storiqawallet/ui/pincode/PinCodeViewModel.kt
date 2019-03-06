package com.storiqa.storiqawallet.ui.pincode

import androidx.databinding.ObservableBoolean
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.network.errors.DialogType
import com.storiqa.storiqawallet.data.network.errors.ResetPinDialogPresenter
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.utils.VibrationUtil
import javax.inject.Inject

class PinCodeViewModel
@Inject
constructor(private val vibrationUtil: VibrationUtil,
            private val userData: IUserDataStorage,
            private val appData: IAppDataStorage) :
        BaseViewModel<IPinCodeNavigator>() {

    private val pinLength = App.res.getInteger(R.integer.PIN_LENGTH)
    private val vibrationDuration: Long = 50
    private val vibrationPattern = longArrayOf(0, 50, 100, 50)

    private var enteredPinCode: String = ""
    val pinCode = NonNullObservableField("")
    val title = NonNullObservableField("")
    val description = NonNullObservableField("")
    val isPinInput = ObservableBoolean(false)

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
                    isPinInput.set(true)
                }
            }
        }

    init {
        pinCode.addOnPropertyChanged {
            if (pinCode.get().length == pinLength)
                onPinCodeEntered()
        }
    }

    fun onDigitEntered(digit: Int) {
        vibrationUtil.vibrate(vibrationDuration)
        if (pinCode.get().length == pinLength)
            return

        pinCode.set(pinCode.get() + digit)
    }

    fun deleteLastDigit() {
        vibrationUtil.vibrate(vibrationDuration)
        pinCode.set(pinCode.get().dropLast(1))
    }

    fun onForgotPinButtonClicked() {
        showErrorDialog(ResetPinDialogPresenter())
    }

    override fun getDialogPositiveButtonClicked(dialogType: DialogType,
                                                params: HashMap<String, String>?): () -> Unit {
        when (dialogType) {
            DialogType.RESET_PIN -> return {
                appData.isPinEntered = false
                getNavigator()?.openLoginActivity()
                getNavigator()?.finishActivity()
            }
            else -> return {}
        }
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
                    vibrationUtil.vibrate(vibrationPattern)
                    pinCode.set("")
                }
            }
            PinCodeState.ENTER -> {
                if (isValidPinCode(pinCode.get())) {
                    getNavigator()?.openMainActivity()
                } else {
                    vibrationUtil.vibrate(vibrationPattern)
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