package com.storiqa.storiqawallet.ui.base

import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.network.errors.ErrorPresenterDialog
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    val hideKeyboard = SingleLiveEvent<Void>()
    val showLoadingDialog = SingleLiveEvent<Boolean>()
    val showErrorDialog = SingleLiveEvent<ErrorPresenterDialog>()

    private var refNavigator: WeakReference<N>? = null

    protected fun hideKeyboard() {
        hideKeyboard.trigger()
    }

    protected fun showLoadingDialog() {
        showLoadingDialog.value = true
    }

    protected fun hideLoadingDialog() {
        showLoadingDialog.value = false
    }

    fun setNavigator(navigator: N) {
        refNavigator = WeakReference(navigator)
    }

    fun getNavigator(): N? {
        return refNavigator?.get()
    }

    fun showErrorDialog(errorPresenter: ErrorPresenterDialog) {
        showErrorDialog.value = errorPresenter
    }

}