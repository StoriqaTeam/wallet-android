package com.storiqa.storiqawallet.db

import android.content.Context
import android.content.SharedPreferences


class AuthPreferences(context: Context) {

    private val preferences: SharedPreferences

    var user: String?
        get() = preferences.getString(KEY_USER, null)
        set(user) {
            val editor = preferences.edit()
            editor.putString(KEY_USER, user)
            editor.commit()
        }

    var token: String?
        get() = preferences.getString(KEY_TOKEN, null)
        set(password) {
            val editor = preferences.edit()
            editor.putString(KEY_TOKEN, password)
            editor.commit()
        }

    init {
        preferences = context
                .getSharedPreferences("auth", Context.MODE_PRIVATE)
    }

    companion object {

        private val KEY_USER = "user"
        private val KEY_TOKEN = "token"
    }
}