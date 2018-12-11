package com.storiqa.storiqawallet.network.errors

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

data class ErrorPresenter(
        @StringRes val title: Int,
        @StringRes val description: Int? = null,
        @StringRes val fieldErrors: HashMap<String, String>,
        @DrawableRes val icon: Int? = null
)