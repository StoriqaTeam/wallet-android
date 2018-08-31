package com.storiqa.storiqawallet.objects

import android.content.res.Resources
import android.support.v4.content.res.ResourcesCompat
import android.widget.Button
import com.storiqa.storiqawallet.R

class ButtonStateSwitcher(private val resources: Resources, val btnSignIn: Button) {

    fun enableButton() {
        btnSignIn.isEnabled = true
        btnSignIn.setTextColor(ResourcesCompat.getColor(resources, android.R.color.white, null))
    }

    fun disableButton() {
        btnSignIn.isEnabled = false
        btnSignIn.setTextColor(ResourcesCompat.getColor(resources, R.color.disableButton, null))
    }
}