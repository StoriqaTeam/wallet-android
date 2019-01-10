package com.storiqa.storiqawallet.ui.base

import com.storiqa.storiqawallet.di.components.ActivityComponent
import com.storiqa.storiqawallet.network.errors.ErrorPresenterDialog

interface IBaseActivity {

    val activityComponent: ActivityComponent

    fun showErrorDialog(error: ErrorPresenterDialog)

    fun showLoadingDialog()

    fun hideLoadingDialog()

    fun hideKeyboard()

}