package com.storiqa.storiqawallet.network.errors

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

sealed class ErrorPresenter

data class ErrorPresenterDialog(
        @StringRes val title: Int = 0, //TODO delete default params
        @StringRes val description: Int = 0,
        @DrawableRes val icon: Int = 0,
        val positiveButton: DialogButton? = null,
        val negativeButton: DialogButton? = null) : ErrorPresenter()

data class ErrorPresenterFields(
        val fieldErrors: HashMap<String, Int>) : ErrorPresenter()

data class DialogButton(
        @StringRes val name: Int,
        var onClick: () -> Unit)