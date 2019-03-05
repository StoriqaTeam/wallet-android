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

    private var prefIsFirstLaunch: Boolean? = null
    private var prefIsPinEntered: Boolean? = null
    private var prefPin: String? = null
    private var prefDeviceId: String? = null
    private var prefToken: String? = null
    private var prefCurrentUserEmail: String? = null
    private var prefOldestPendingTransactionTime: Long? = null
    private var prefPrivateKey: String? = null
    private var prefPrivateKeyEmail = ""

    override var isFirstLaunch: Boolean
        get() {
            if (prefIsFirstLaunch == null) prefIsFirstLaunch = prefs.getPreferences().getBoolean(FIRST_LAUNCH, true)
            return prefIsFirstLaunch!!
        }
        set(value) {
            prefIsFirstLaunch = value
            prefs.getEditor().putBoolean(FIRST_LAUNCH, value).apply()
        }

    override var isPinEntered: Boolean
        get() {
            if (prefIsPinEntered == null) prefIsPinEntered = prefs.getPreferences().getBoolean(PIN_ENTERED, false)
            return prefIsPinEntered!!
        }
        set(value) {
            prefIsPinEntered = value
            prefs.getEditor().putBoolean(PIN_ENTERED, value).apply()
        }
    override var pin: String
        get() {
            if (prefPin == null) prefPin = prefs.getPreferences().getString(USER_PIN, null)
            return prefPin!!
        }
        set(value) {
            prefPin = value
            prefs.getEditor().putString(USER_PIN, value).apply()
        }

    override var deviceId: String
        get() {
            if (prefDeviceId == null) prefDeviceId = prefs.getPreferences().getString(DEVICE_ID, null)
            return prefDeviceId!!
        }
        set(value) {
            prefDeviceId = value
            prefs.getEditor().putString(DEVICE_ID, value).apply()
        }

    override var token: String
        get() {
            if (prefToken == null) prefToken = prefs.getPreferences().getString(TOKEN, "")
            return prefToken!!
        }
        set(value) {
            prefToken = value
            prefs.getEditor().putString(TOKEN, value).apply()
        }

    override var currentUserEmail: String
        get() {
            if (prefCurrentUserEmail == null) prefCurrentUserEmail = prefs.getPreferences().getString(CURRENT_USER_EMAIL, null)
            return prefCurrentUserEmail!!
        }
        set(value) {
            prefCurrentUserEmail = value
            prefs.getEditor().putString(CURRENT_USER_EMAIL, value).apply()
        }

    override var oldestPendingTransactionTime: Long
        get() {
            if (prefOldestPendingTransactionTime == null) prefOldestPendingTransactionTime = prefs.getPreferences().getLong(LAST_PENDING_TRANSACTION, 0)
            return prefOldestPendingTransactionTime!!
        }
        set(value) {
            prefOldestPendingTransactionTime = value
            prefs.getEditor().putLong(LAST_PENDING_TRANSACTION, value).apply()
        }

    override val deviceOs: String
        get() = getDeviceOs()

    override fun getPrivateKey(email: String): String? {
        if (prefPrivateKey == null || prefPrivateKeyEmail != email) {
            prefPrivateKeyEmail = email
            prefPrivateKey = prefs.getPreferences().getString(PRIVATE_KEY + email, null)
        }
        return prefPrivateKey
    }

    override fun setPrivateKey(email: String, key: String) {
        prefPrivateKey = key
        prefPrivateKeyEmail = email
        prefs.getEditor().putString(PRIVATE_KEY + email, key).apply()
    }
}