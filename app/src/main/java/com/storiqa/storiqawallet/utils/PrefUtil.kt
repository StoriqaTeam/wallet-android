package com.storiqa.storiqawallet.utils

import android.content.Context
import android.content.SharedPreferences
import com.storiqa.storiqawallet.di.qualifiers.AppContext

class PrefUtil(@AppContext context: Context) {

    private val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun getPreferences(): SharedPreferences {
        return preferences
    }

    fun getEditor(): SharedPreferences.Editor {
        return preferences.edit()
    }
}