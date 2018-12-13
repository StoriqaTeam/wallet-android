package com.storiqa.storiqawallet.ui.base

import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.network.errors.*
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    val hideKeyboard = SingleLiveEvent<Void>()
    val showLoadingDialog = SingleLiveEvent<Boolean>()
    val showErrorDialog = SingleLiveEvent<ErrorPresenter>()

    private val errorHandler = ErrorHandler()

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

    private fun showErrorDialog(errorPresenter: ErrorPresenterDialog) {
        showErrorDialog.value = errorPresenter
    }

    open fun showErrorFields(errorPresenter: ErrorPresenterFields) {

    }

    protected fun handleError(exception: Exception) {
        hideLoadingDialog()

        val errorPresenter = errorHandler.handleError(exception)
        when (errorPresenter) {
            is ErrorPresenterFields -> showErrorFields(errorPresenter)
            is ErrorPresenterDialog -> {
                errorPresenter.positiveButton?.onClick =
                        getDialogPositiveButtonClicked(errorPresenter.dialogType)
                errorPresenter.negativeButton?.onClick =
                        getDialogNegativeButtonClicked(errorPresenter.dialogType)
                showErrorDialog(errorPresenter)
            }
        }
    }

    open fun getDialogPositiveButtonClicked(dialogType: DialogType): () -> Unit {
        return {}
    }

    open fun getDialogNegativeButtonClicked(dialogType: DialogType): () -> Unit {
        return {}
    }

}