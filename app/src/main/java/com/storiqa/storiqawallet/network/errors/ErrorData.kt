package com.storiqa.storiqawallet.network.errors

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

data class ErrorData(
        @StringRes val title: Int,
        @StringRes val description: Int? = null,
        @DrawableRes val icon: Int? = null)