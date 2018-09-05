package com.storiqa.storiqawallet.db

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(val context: Context) {

    val isUserFinishedQuickLaunchSetupOrSkippedIt = "com.storiqa.storiqawallet.db.isUserFinishedQuickLaunchSetupOrSkippedIt"
    val isUserWentFromSplash = "com.storiqa.storiqawallet.db.isUserWentFromSplash"

    private val PREFS_FILENAME = "com.storiqa.storiqawallet.db"

    private fun getPrefs(): SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun setQuickLaunchFinished() = getPrefs().edit().putBoolean(isUserFinishedQuickLaunchSetupOrSkippedIt, true).apply()

    fun isQuickLaunchFinished(): Boolean = getPrefs().getBoolean(isUserFinishedQuickLaunchSetupOrSkippedIt, false)

    fun setIsUserWentFromSplash() = getPrefs().edit().putBoolean(isUserWentFromSplash, true).apply()

    fun isUserWentFromSplash() = getPrefs().getBoolean(isUserWentFromSplash, false)

}