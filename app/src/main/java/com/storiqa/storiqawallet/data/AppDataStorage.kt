package com.storiqa.storiqawallet.data

import com.storiqa.storiqawallet.utils.PrefUtil
import com.storiqa.storiqawallet.utils.getDeviceOs

private const val FIRST_LAUNCH = "first_launch"
private const val PIN_ENTERED = "pin_entered"
private const val USER_PIN = "user_pin"
private const val DEVICE_ID = "device_id"
private const val PRIVATE_KEY = "private_key"
private const val TOKEN = "token"
private const val CURRENT_USER_EMAIL = "current_user_email"
private const val LAST_PENDING_TRANSACTION = "last_pending_transaction"

class AppDataStorage(private val prefs: PrefUtil) : IAppDataStorage {

    override var isFirstLaunch: Boolean
        get() = prefs.getPreferences().getBoolean(FIRST_LAUNCH, true)
        set(value) {
            prefs.getEditor().putBoolean(FIRST_LAUNCH, value).apply()
        }

    override var isPinEntered: Boolean
        get() = prefs.getPreferences().getBoolean(PIN_ENTERED, false)
        set(value) {
            prefs.getEditor().putBoolean(PIN_ENTERED, value).apply()
        }
    override var pin: String
        get() = prefs.getPreferences().getString(USER_PIN, null)!!
        set(value) {
            prefs.getEditor().putString(USER_PIN, value).apply()
        }

    override var deviceId: String
        get() = prefs.getPreferences().getString(DEVICE_ID, null)!!
        set(value) {
            prefs.getEditor().putString(DEVICE_ID, value).apply()
        }

    override var token: String
        get() = prefs.getPreferences().getString(TOKEN, null)!!
        set(value) {
            prefs.getEditor().putString(TOKEN, value).apply()
        }

    override var currentUserEmail: String
        get() = prefs.getPreferences().getString(CURRENT_USER_EMAIL, null)!!
        set(value) {
            prefs.getEditor().putString(CURRENT_USER_EMAIL, value).apply()
        }

    override var lastPendingTransactionTime: Long
        get() = prefs.getPreferences().getLong(LAST_PENDING_TRANSACTION, Long.MAX_VALUE)
        set(value) {
            prefs.getEditor().putLong(LAST_PENDING_TRANSACTION, value).apply()
        }

    override val deviceOs: String
        get() = getDeviceOs()

    override fun getPrivateKey(email: String): String? {
        return prefs.getPreferences().getString(PRIVATE_KEY + email, null)
    }

    override fun setPrivateKey(email: String, key: String) {
        prefs.getEditor().putString(PRIVATE_KEY + email, key).apply()
    }
}