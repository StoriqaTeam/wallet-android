package com.storiqa.storiqawallet.screen_pin_code_enter

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.enums.PinCodeEnterType

class EnterPinViewModel(enterType : PinCodeEnterType) : ViewModel() {

    var pinCode = ObservableField<String>("")
    var enteredPinCode = ObservableField<String>("")

    val isEnterForLogin = ObservableField<Boolean>(enterType == PinCodeEnterType.LOGIN)
    val isEnterForPasswordSet = ObservableField<Boolean>(enterType == PinCodeEnterType.ENTER_PASSWORD_FIRST_TIME)
    val isEnterForPasswordRepeat = ObservableField<Boolean>(enterType == PinCodeEnterType.REPEAT_PASSWORD_FIRST_TIME)

    val shouldRedirectToMainScreen = MutableLiveData<Boolean>()
    val shouldRedirectToFingerPrintSetup = MutableLiveData<Boolean>()

    val isPinNotValidError = ObservableField<Boolean>(false)
    val isPinsNotMatchError = ObservableField<Boolean>(false)

    fun enterDigit(digit: String) = pinCode.set(pinCode.get() + digit)

    fun eraseLastDigit() {
        pinCode.set(pinCode.get()!!.substring(0, pinCode.get()!!.lastIndex))
    }

    fun performLogin() {
        //TODO check pin
        var isPinValid = true
        if(isPinValid) {
            shouldRedirectToMainScreen.value = true
        } else {
            isPinNotValidError.set(true)
        }
    }

    fun nextSetPasswordState() {
        isPinNotValidError.set(false)
        isPinsNotMatchError.set(false)

        if(isEnterForPasswordSet.get()!!) {
            enteredPinCode.set(pinCode.get())
            pinCode.set("")
            isEnterForPasswordRepeat.set(true)
            isEnterForPasswordSet.set(false)
        } else if(isEnterForPasswordRepeat.get()!!) {
            if(pinCode.get()!! == enteredPinCode.get()) {
                shouldRedirectToFingerPrintSetup.value = true
            } else {
                isEnterForPasswordSet.set(true)
                isEnterForPasswordRepeat.set(false)
                pinCode.set("")
                enteredPinCode.set("")
                isPinsNotMatchError.set(true)
            }
        }
    }
}