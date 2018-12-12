package com.storiqa.storiqawallet.ui.dialogs

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import com.storiqa.storiqawallet.common.SingleLiveEvent
import javax.inject.Inject

class MessageViewModel
@Inject
constructor() : ViewModel() {

    lateinit var title: ObservableField<String>
    lateinit var message: ObservableField<String>
    lateinit var icon: ObservableField<Drawable>
    lateinit var btnPositiveText: ObservableField<String>
    val btnNegativeText = ObservableField<String>("")

    val positiveButtonClicked = SingleLiveEvent<Void>()
    val negativeButtonClicked = SingleLiveEvent<Void>()

    fun initData(title: String, message: String, icon: Drawable,
                 btnPositiveText: String, btnNegativeText: String?) {
        this.title = ObservableField(title)
        this.message = ObservableField(message)
        this.icon = ObservableField(icon)
        this.btnPositiveText = ObservableField(btnPositiveText)

        if (btnNegativeText != null)
            this.btnNegativeText.set(btnNegativeText)
    }

    fun onPositiveButtonClicked() {
        positiveButtonClicked.trigger()
    }

    fun onNegativeButtonClicked() {
        negativeButtonClicked.trigger()
    }
}