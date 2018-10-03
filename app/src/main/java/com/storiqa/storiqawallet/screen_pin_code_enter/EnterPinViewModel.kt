package com.storiqa.storiqawallet.screen_pin_code_enter

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.storiqa.storiqawallet.enums.PinCodeEnterType
import com.storiqa.storiqawallet.screen_scan_finger.ScanFingerModel

class EnterPinViewModel(private val view: EnterPinCodeView) : ViewModel() {
    val model = PinCodeModel()
    var pinCode = ObservableField<String>("")
    var enteredPinCode = ObservableField<String>("")

    val enterType = ObservableField<PinCodeEnterType>(PinCodeEnterType.LOGIN)

    val isEnterForLogin = ObservableField<Boolean>(view.enterType() == PinCodeEnterType.LOGIN)
    val isEnterForPasswordSet = ObservableField<Boolean>(view.enterType() == PinCodeEnterType.SET_PASSWORD)
    val isEnterForPasswordRepeat = ObservableField<Boolean>(view.enterType() == PinCodeEnterType.REPEAT_PASSWORD)

    val isPinNotValidError = ObservableField<Boolean>(false)
    val isPinsNotMatchError = ObservableField<Boolean>(false)
    val isContinueButtonVisible = ObservableField<Boolean>(false)

    fun enterDigit(digit: String) {
        pinCode.set(pinCode.get() + digit)
        isPinNotValidError.set(false)
        isPinsNotMatchError.set(false)

        if(pinCode.get()!!.length == 4) {
            when {
                isEnterForLogin.get()!! -> performLogin()
                isEnterForPasswordSet.get()!! -> isContinueButtonVisible.set(true)
                isEnterForPasswordRepeat.get()!! -> nextSetPasswordState()
            }
        }
    }

    fun eraseLastDigit() {
        pinCode.set(pinCode.get()!!.substring(0, pinCode.get()!!.lastIndex))
    }

    fun performLogin() {
        val isPinValid = model.pinCodeIsValid(pinCode.get()!!)
        if(isPinValid) {
            view.redirectOnMainScreen()
        } else {
            isPinNotValidError.set(true)
            view.vibrate()
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
                model.savePincode(pinCode.get().toString())
                model.onPinCodeSetted()
                view.redirectToFingerPrintSetup()
            } else {
                isEnterForPasswordSet.set(true)
                isEnterForPasswordRepeat.set(false)
                pinCode.set("")
                enteredPinCode.set("")
                isPinsNotMatchError.set(true)
                view.vibrate()
            }
        }
    }

    fun startListenForFingerprint(success: () -> Unit, failure: () -> Unit) {
        ScanFingerModel().startListenForFingerprint(success, failure)
    }

    fun eraseUserQuickLaunch() {
        model.eraseUserQuickLaunch()
    }

}