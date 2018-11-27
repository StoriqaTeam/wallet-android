package com.storiqa.storiqawallet

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Fragment.hideKeyboard(view: View) {
    activity!!.hideKeyboard(view)
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

