package com.storiqa.storiqawallet.objects

import android.content.Context
import android.content.Intent
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.enums.PinCodeEnterType
import com.storiqa.storiqawallet.screen_main.MainActivity
import com.storiqa.storiqawallet.screen_pin_code_enter.EnterPinCodeActivity
import com.storiqa.storiqawallet.screen_scan_finger.ScanFingerActivity

class ScreenStarter {

    fun startEnterPinCodeScreen(context: Context) = context.startActivity(Intent(context, EnterPinCodeActivity::class.java).putExtra(Extras().pinCodeEnterType, PinCodeEnterType.SET_PASSWORD))

    fun startMainScreen(context: Context) = context.startActivity(Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

    fun startFingerScanScreen(context: Context) = context.startActivity(Intent(context, ScanFingerActivity::class.java))

    fun startEnterPinCodeScreenForLogin(context: Context) = context.startActivity(Intent(context, EnterPinCodeActivity::class.java).putExtra(Extras().pinCodeEnterType, PinCodeEnterType.LOGIN).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}