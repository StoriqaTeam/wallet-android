package com.storiqa.storiqawallet.objects

import android.support.v4.content.res.ResourcesCompat
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.RxTextView
import com.storiqa.storiqawallet.R
import io.reactivex.android.schedulers.AndroidSchedulers

class ButtonStateSwitcherFor(val button: Button) {

    var editTextViews : List<EditText> = arrayListOf()
    var checkBoxes : List<CheckBox> = arrayListOf()

    fun observeNotEmpty(vararg editTextViews : EditText) : ButtonStateSwitcherFor {
        this.editTextViews = editTextViews.asList()

        for(editText in editTextViews) {
            RxTextView.afterTextChangeEvents(editText)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { refreshButtonState() }
        }
        return this
    }

    private fun refreshButtonState() {
        if (isNoTextEmpty() && allCheckboxesChecked()) {
            enableButton()
        } else {
            disableButton()
        }
    }

    private fun allCheckboxesChecked(): Boolean {
        for(checkbox in checkBoxes) {
            if(!checkbox.isChecked) {
                return false
            }
        }

        return true
    }

    fun observeIsChecked(vararg checkBoxes : CheckBox) : ButtonStateSwitcherFor {
        this.checkBoxes = checkBoxes.asList()

        for(checkBox in checkBoxes) {
            checkBox.setOnCheckedChangeListener { compoundButton, state -> refreshButtonState() }
        }

        return this
    }

    private fun isNoTextEmpty(): Boolean {
        for (editText in editTextViews) {
            if (editText.text.isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun enableButton() {
        button.isEnabled = true
        button.setTextColor(ResourcesCompat.getColor(button.context.resources, android.R.color.white, null))
    }

    private fun disableButton() {
        button.isEnabled = false
        button.setTextColor(ResourcesCompat.getColor(button.context.resources, R.color.disableButton, null))
    }
}