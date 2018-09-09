package com.storiqa.storiqawallet.db

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(val context: Context) {

    private val isUserFinishedQuickLaunchSetupOrSkippedIt = "com.storiqa.storiqawallet.db.isUserFinishedQuickLaunchSetupOrSkippedIt"
    private val isUserWentFromSplash = "com.storiqa.storiqawallet.db.isUserWentFromSplash"
    private val isPinCodeEnabled = "com.storiqa.storiqawallet.db.isPinCodeEnabled"
    private val isFingerprintEnabled = "com.storiqa.storiqawallet.db.isFingerprintEnabled"
    private val pinCode = "com.storiqa.storiqawallet.db.pinCode"
    private val fingerPrint = "com.storiqa.storiqawallet.db.fingerPrint"

    private val PREFS_FILENAME = "com.storiqa.storiqawallet.db"

    private fun getPrefs(): SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun setQuickLaunchFinished() = getPrefs().edit().putBoolean(isUserFinishedQuickLaunchSetupOrSkippedIt, true).apply()

    fun isQuickLaunchFinished(): Boolean = getPrefs().getBoolean(isUserFinishedQuickLaunchSetupOrSkippedIt, false)

    fun setIsUserWentFromSplash() = getPrefs().edit().putBoolean(isUserWentFromSplash, true).apply()

    fun isUserWentFromSplash() = getPrefs().getBoolean(isUserWentFromSplash, false)

    fun isPinCodeEnabled() = getPrefs().getBoolean(isPinCodeEnabled, false)

    fun isFingerprintEnabled() = getPrefs().getBoolean(isFingerprintEnabled, false)

    fun setPinCodeEnabled(isEnabled: Boolean) = getPrefs().edit().putBoolean(isPinCodeEnabled, isEnabled).apply()

    fun setFingerprintEnabled(isEnabled: Boolean) = getPrefs().edit().putBoolean(isFingerprintEnabled, isEnabled).apply()

    fun savePinCode(pincode: String) = getPrefs().edit().putString(pinCode, pincode).apply()

    fun getPinCode() = getPrefs().getString(pinCode, "")

    fun saveFingerPrintToken(fingerprint: String) = getPrefs().edit().putString(fingerPrint, fingerprint).apply()

    fun getFingerPrintToken() = getPrefs().getString(fingerPrint, "")
}