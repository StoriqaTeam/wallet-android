package com.storiqa.storiqawallet.ui.base

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.storiqa.storiqawallet.common.SingleLiveEvent
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    val hideKeyboard = SingleLiveEvent<Void>()
    val isLoading = ObservableBoolean(false)

    private var refNavigator: WeakReference<N>? = null

    protected fun hideKeyboard() {
        hideKeyboard.call()
    }

    protected fun showLoadingDialog() {
        isLoading.set(true)
    }

    protected fun hideLoadingDialog() {
        isLoading.set(false)
    }

    fun setNavigator(navigator: N) {
        refNavigator = WeakReference(navigator)
    }

    fun getNavigator(): N? {
        return refNavigator?.get()
    }

}