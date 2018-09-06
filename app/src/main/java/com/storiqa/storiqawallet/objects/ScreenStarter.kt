package com.storiqa.storiqawallet.objects

import android.content.Context
import android.content.Intent
import com.storiqa.storiqawallet.constants.Extras
import com.storiqa.storiqawallet.enums.PinCodeEnterType
import com.storiqa.storiqawallet.screen_login.LoginActivity
import com.storiqa.storiqawallet.screen_main.MainActivity
import com.storiqa.storiqawallet.screen_pin_code_enter.EnterPinCodeActivity
import com.storiqa.storiqawallet.screen_pincode_setup.PinCodeSetupActivity
import com.storiqa.storiqawallet.screen_quick_start.QuickStartActivity
import com.storiqa.storiqawallet.screen_recover_password.screen_recover_email_enter_first_step.RecoverPasswordActivity
import com.storiqa.storiqawallet.screen_recover_password.screen_recover_password_enter_second_step.NewPasswordEnterActivity
import com.storiqa.storiqawallet.screen_register.RegisterActivity
import com.storiqa.storiqawallet.screen_scan_finger.ScanFingerActivity
import com.storiqa.storiqawallet.screen_setup_fingerprint.FingerprintSetupActivity

class ScreenStarter {

    fun startLoginScreen(context: Context) = context.startActivity(Intent(context, LoginActivity::class.java))

    fun startRegisterScreen(context: Context) = context.startActivity(Intent(context, RegisterActivity::class.java))

    fun startRecoverPasswordScreen(context: Context) = context.startActivity(Intent(context, RecoverPasswordActivity::class.java))

    fun startNewPasswordEnterScreen(context: Context, email: String) = context.startActivity(Intent(context, NewPasswordEnterActivity::class.java).putExtra(Extras().email, email))

    fun startPinCodeSetupScreen(context: Context) = context.startActivity(Intent(context, PinCodeSetupActivity::class.java))

    fun startEnterPinCodeScreen(context: Context) = context.startActivity(Intent(context, EnterPinCodeActivity::class.java).putExtra(Extras().pinCodeEnterType, PinCodeEnterType.ENTER_PASSWORD_FIRST_TIME))

    fun startQuickStartScreen(context: Context) = context.startActivity(Intent(context, QuickStartActivity::class.java))

    fun startFingerprintSetupScreen(context: Context) = context.startActivity(Intent(context, FingerprintSetupActivity::class.java))

    fun startMainScreen(context: Context)  = context.startActivity(Intent(context, MainActivity::class.java))

    fun startFingerScanScreen(context: Context)  = context.startActivity(Intent(context, ScanFingerActivity::class.java))
}