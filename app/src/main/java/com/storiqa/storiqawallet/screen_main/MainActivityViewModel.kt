package com.storiqa.storiqawallet.screen_main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.enums.Screen

class MainActivityViewModel : ViewModel() {

    val selectedScreen = MutableLiveData<Screen>()

    fun openMyWalletScreen() {
        selectedScreen.value = Screen.MY_WALLET
    }

    fun openDepositScreen() {
        selectedScreen.value = Screen.DEPOSIT
    }

    fun openExchangeScreen() {
        selectedScreen.value = Screen.EXCHANGE
    }

    fun openSendScreen() {
        selectedScreen.value = Screen.SEND
    }

    fun openMenuScreen() {
        selectedScreen.value = Screen.MENU
    }
}