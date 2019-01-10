package com.storiqa.storiqawallet.data

import com.storiqa.storiqawallet.utils.PrefUtil

private const val ID = "id"
private const val EMAIL = "email"
private const val PASSWORD = "password"
private const val FIRST_NAME = "first_name"
private const val LAST_NAME = "last_name"

class UserDataStorage(private val prefs: PrefUtil) : IUserDataStorage {

    override var id: Int
        get() = prefs.getPreferences().getInt(ID, -1)
        set(value) {
            prefs.getEditor().putInt(ID, value).apply()
        }

    override var email: String
        get() = prefs.getPreferences().getString(EMAIL, null)!!
        set(value) {
            prefs.getEditor().putString(EMAIL, value).apply()
        }

    override var firstName: String
        get() = prefs.getPreferences().getString(FIRST_NAME, null)!!
        set(value) {
            prefs.getEditor().putString(FIRST_NAME, value).apply()
        }

    override var lastName: String
        get() = prefs.getPreferences().getString(LAST_NAME, null)!!
        set(value) {
            prefs.getEditor().putString(LAST_NAME, value).apply()
        }
}