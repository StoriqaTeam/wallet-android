package com.storiqa.storiqawallet.ui.base

import android.arch.lifecycle.ViewModel
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.network.errors.DialogType
import com.storiqa.storiqawallet.network.errors.ErrorHandler
import com.storiqa.storiqawallet.network.errors.ErrorPresenterDialog
import com.storiqa.storiqawallet.network.errors.ErrorPresenterFields
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    val hideKeyboard = SingleLiveEvent<Void>()
    val showLoadingDialog = SingleLiveEvent<Boolean>()
    val showMessageDialog = SingleLiveEvent<ErrorPresenterDialog>()

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

    fun showMessageDialog(errorPresenter: ErrorPresenterDialog) {
        showMessageDialog.value = errorPresenter.apply {
            positiveButton?.onClick =
                    getDialogPositiveButtonClicked(errorPresenter.dialogType, errorPresenter.params)
            negativeButton?.onClick =
                    getDialogNegativeButtonClicked(errorPresenter.dialogType)
        }
    }

    open fun showErrorFields(errorPresenter: ErrorPresenterFields) {

    }

    protected fun handleError(exception: Exception) {
        hideLoadingDialog()

        val errorPresenter = errorHandler.handleError(exception)
        when (errorPresenter) {
            is ErrorPresenterFields -> showErrorFields(errorPresenter)
            is ErrorPresenterDialog -> {
                showMessageDialog(errorPresenter)
            }
        }
    }

    open fun getDialogPositiveButtonClicked(dialogType: DialogType, params: HashMap<String, String>?): () -> Unit {
        return {}
    }

    open fun getDialogNegativeButtonClicked(dialogType: DialogType): () -> Unit {
        return {}
    }

}