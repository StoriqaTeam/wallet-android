package com.storiqa.storiqawallet.objects

import android.content.res.Resources
import android.support.v4.content.res.ResourcesCompat
import android.widget.Button
import com.storiqa.storiqawallet.R

class ButtonStateSwitcher(private val resources: Resources, val button: Button) {

    fun enableButton() {
        button.isEnabled = true
        button.setTextColor(ResourcesCompat.getColor(resources, android.R.color.white, null))
    }

    fun disableButton() {
        button.isEnabled = false
        button.setTextColor(ResourcesCompat.getColor(resources, R.color.disableButton, null))
    }
}