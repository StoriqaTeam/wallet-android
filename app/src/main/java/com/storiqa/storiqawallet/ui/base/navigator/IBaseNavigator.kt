package com.storiqa.storiqawallet.ui.base.navigator

interface IBaseNavigator {
    fun showMessageDialog(title: String, description: String, iconRes: Int,
                          positiveButtonName: String, positiveButtonCallback: () -> Unit,
                          negativeButtonName: String?,
                          negativeButtonCallback: (() -> Unit)?)

    fun finishActivity()
}