package com.storiqa.storiqawallet.ui.dialogs

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.graphics.drawable.Drawable

class MessageViewModel : ViewModel() {

    val title = ObservableField<String>("")
    val message = ObservableField<String>("")
    val icon = ObservableField<Drawable>(null)

    fun init(title: String, message: String, icon: Drawable) {
        this.title.set(title)
        

    }
}