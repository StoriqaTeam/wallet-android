package com.storiqa.storiqawallet.data.preferences

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

    override var isFirstLaunch: Boolean = prefs.getPreferences().getBoolean(FIRST_LAUNCH, true)
        set(value) {
            field = value
            prefs.getEditor().putBoolean(FIRST_LAUNCH, value).apply()
        }

    override var isPinEntered: Boolean = prefs.getPreferences().getBoolean(PIN_ENTERED, false)
        set(value) {
            field = value
            prefs.getEditor().putBoolean(PIN_ENTERED, value).apply()
        }
    override var pin: String = prefs.getPreferences().getString(USER_PIN, null)!!
        set(value) {
            field = value
            prefs.getEditor().putString(USER_PIN, value).apply()
        }

    override var deviceId: String = prefs.getPreferences().getString(DEVICE_ID, null)!!
        set(value) {
            field = value
            prefs.getEditor().putString(DEVICE_ID, value).apply()
        }

    override var token: String = prefs.getPreferences().getString(TOKEN, null)!!
        set(value) {
            field = value
            prefs.getEditor().putString(TOKEN, value).apply()
        }

    override var currentUserEmail: String = prefs.getPreferences().getString(CURRENT_USER_EMAIL, null)!!
        set(value) {
            field = value
            prefs.getEditor().putString(CURRENT_USER_EMAIL, value).apply()
        }

    override var oldestPendingTransactionTime: Long = prefs.getPreferences().getLong(LAST_PENDING_TRANSACTION, 0)
        set(value) {
            field = value
            prefs.getEditor().putLong(LAST_PENDING_TRANSACTION, value).apply()
        }

    override val deviceOs: String
        get() = getDeviceOs()


    private var privateKey = ""
    private var privateKeyEmail = ""

    override fun getPrivateKey(email: String): String? {
        return if (privateKeyEmail.equals(email, true))
            privateKey
        else
            prefs.getPreferences().getString(PRIVATE_KEY + email, null)
    }
    override fun setPrivateKey(email: String, key: String) {
        privateKey = key
        privateKeyEmail = email
        prefs.getEditor().putString(PRIVATE_KEY + email, key).apply()
    }
}