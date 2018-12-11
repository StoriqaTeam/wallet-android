package com.storiqa.storiqawallet.ui.dialogs

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import javax.inject.Inject

class MessageViewModel
@Inject
constructor() : ViewModel() {

    lateinit var title: ObservableField<String>
    lateinit var message: ObservableField<String>
    lateinit var icon: ObservableField<Drawable>

    fun initData(title: String, message: String, icon: Drawable) {
        this.title = ObservableField(title)
        this.message = ObservableField(message)
        this.icon = ObservableField(icon)
    }

    fun onPositiveButtonClicked() {

    }

    fun onNegativeButtonClicked() {

    }
}