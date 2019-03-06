package com.storiqa.storiqawallet.ui.base.navigator

import com.storiqa.storiqawallet.ui.dialogs.message.MessageDialog

abstract class BaseNavigator(private val navigator: INavigator) : IBaseNavigator {

    override fun showMessageDialog(title: String, description: String, iconRes: Int,
                                   positiveButtonName: String, positiveButtonCallback: () -> Unit,
                                   negativeButtonName: String?,
                                   negativeButtonCallback: (() -> Unit)?) {
        val dialog = MessageDialog.newInstance(title, description, iconRes).apply {
            setPositiveButton(positiveButtonName, positiveButtonCallback)
            if (negativeButtonName != null && negativeButtonCallback != null)
                setNegativeButton(negativeButtonName, negativeButtonCallback)
        }
        navigator.showDialogFragment(dialog, "message dialog")
    }

    override fun finishActivity() {
        navigator.finishActivity()
    }
}