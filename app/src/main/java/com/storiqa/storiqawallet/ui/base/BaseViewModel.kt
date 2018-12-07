package com.storiqa.storiqawallet.ui.base

import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.common.SingleLiveEvent
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    val hideKeyboard = SingleLiveEvent<Void>()
    val showLoadingDialog = SingleLiveEvent<Boolean>()

    private var refNavigator: WeakReference<N>? = null

    protected fun hideKeyboard() {
        hideKeyboard.call()
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

}