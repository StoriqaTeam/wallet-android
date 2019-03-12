package com.storiqa.storiqawallet.ui.base

import androidx.lifecycle.ViewModel
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.network.errors.ErrorHandler
import com.storiqa.storiqawallet.ui.base.navigator.IBaseNavigator
import com.storiqa.storiqawallet.ui.dialogs.message.DialogType
import com.storiqa.storiqawallet.ui.dialogs.message.ErrorPresenterFields
import com.storiqa.storiqawallet.ui.dialogs.message.MessageDialogPresenter
import java.lang.ref.WeakReference

abstract class BaseViewModel<N : IBaseNavigator> : ViewModel() {

    val hideKeyboard = SingleLiveEvent<Void>()
    val showLoadingDialog = SingleLiveEvent<Boolean>()

    private val errorHandler = ErrorHandler()

    private var refNavigator: WeakReference<N>? = null

    open fun onViewStarted() {

    }

    open fun onViewStopped() {

    }

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

    fun showMessageDialog(presenter: MessageDialogPresenter) {
        val negativeButtonName = presenter.negativeButton?.name
        getNavigator()?.showMessageDialog(
                App.res.getString(presenter.title),
                App.res.getString(presenter.description),
                presenter.icon,
                App.res.getString(presenter.positiveButton?.name ?: R.string.button_ok),
                getDialogPositiveButtonClicked(presenter.dialogType, presenter.params),
                if (negativeButtonName != null) App.res.getString(negativeButtonName) else null,
                getDialogNegativeButtonClicked(presenter.dialogType))

    }

    open fun showErrorFields(errorPresenter: ErrorPresenterFields) {

    }

    protected fun handleError(exception: Exception) {
        exception.printStackTrace()
        hideLoadingDialog()

        val errorPresenter = errorHandler.handleError(exception)
        when (errorPresenter) {
            is ErrorPresenterFields -> showErrorFields(errorPresenter)
            is MessageDialogPresenter -> {
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