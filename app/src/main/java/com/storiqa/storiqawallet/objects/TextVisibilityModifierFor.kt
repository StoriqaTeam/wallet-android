package com.storiqa.storiqawallet.objects

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import com.storiqa.storiqawallet.R
import org.jetbrains.anko.imageResource

class TextVisibilityModifierFor(val etPassword: EditText) {

    var isPasswordVisible = false

    fun observeClickOn(showOrHideButton: ImageView) {
        showOrHideButton.setOnClickListener { changeVisibility(showOrHideButton) }
    }

    private fun changeVisibility(showOrHideButton: ImageView) {
        if (isPasswordVisible) {
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            showOrHideButton.imageResource = R.drawable.eye_opened
        } else {
            etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showOrHideButton.imageResource = R.drawable.closed_eye
        }
        etPassword.setSelection(etPassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }
}