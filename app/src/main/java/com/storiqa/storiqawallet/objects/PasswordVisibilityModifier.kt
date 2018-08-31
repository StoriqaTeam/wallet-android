package com.storiqa.storiqawallet.objects

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import com.storiqa.storiqawallet.R

class PasswordVisibilityModifier(val etPassword : EditText, val showOrHideButton : View) {

    var isPasswordVisible = false

    fun changeVisibility() {
        if(isPasswordVisible) {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            showOrHideButton.setBackgroundResource(R.drawable.eye_opened)
        } else {
            etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showOrHideButton.setBackgroundResource(R.drawable.closed_eye)
        }
        etPassword.setSelection(etPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }
}