package com.storiqa.storiqawallet.data.preferences

import com.storiqa.storiqawallet.utils.PrefUtil

private const val ID = "id"
private const val EMAIL = "email"
private const val FIRST_NAME = "first_name"
private const val LAST_NAME = "last_name"

class UserDataStorage(private val prefs: PrefUtil) : IUserDataStorage {

    private var prefId: Long? = null
    private var prefEmail: String? = null
    private var prefFirstName: String? = null
    private var prefLastName: String? = null

    override var id: Long
        get() {
            if (prefId == null) prefId = prefs.getPreferences().getLong(ID, -1)
            return prefId!!
        }
        set(value) {
            prefId = value
            prefs.getEditor().putLong(ID, value).apply()
        }

    override var email: String
        get() {
            if (prefEmail == null) prefEmail = prefs.getPreferences().getString(EMAIL, null)
            return prefEmail!!
        }
        set(value) {
            prefEmail = value
            prefs.getEditor().putString(EMAIL, value).apply()
        }

    override var firstName: String
        get() {
            if (prefFirstName == null) prefFirstName = prefs.getPreferences().getString(FIRST_NAME, null)
            return prefFirstName!!
        }
        set(value) {
            prefFirstName = value
            prefs.getEditor().putString(FIRST_NAME, value).apply()
        }

    override var lastName: String
        get() {
            if (prefLastName == null) prefLastName = prefs.getPreferences().getString(LAST_NAME, null)
            return prefLastName!!
        }
        set(value) {
            prefLastName = value
            prefs.getEditor().putString(LAST_NAME, value).apply()
        }
}