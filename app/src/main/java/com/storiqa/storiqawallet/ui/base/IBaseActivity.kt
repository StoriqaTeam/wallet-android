package com.storiqa.storiqawallet.ui.base

import androidx.appcompat.widget.Toolbar
import com.storiqa.storiqawallet.data.network.errors.ErrorPresenterDialog
import com.storiqa.storiqawallet.di.components.ActivityComponent

interface IBaseActivity {

    val activityComponent: ActivityComponent

    fun showErrorDialog(error: ErrorPresenterDialog)

    fun showLoadingDialog()

    fun hideLoadingDialog()

    fun hideKeyboard()

    fun setupActionBar(toolbar: Toolbar, title: String? = null, backButtonEnabled: Boolean = false)

}