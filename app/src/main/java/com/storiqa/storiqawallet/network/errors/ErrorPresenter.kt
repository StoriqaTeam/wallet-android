package com.storiqa.storiqawallet.network.errors

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

sealed class ErrorPresenter

data class ErrorPresenterDialog(
        @StringRes val title: Int = 0,
        @StringRes val description: Int = 0,
        @DrawableRes val icon: Int = 0) : ErrorPresenter()

data class ErrorPresenterFields(
        val fieldErrors: HashMap<String, Int>) : ErrorPresenter()