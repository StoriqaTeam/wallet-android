package com.storiqa.storiqawallet.objects;

import com.google.android.material.textfield.TextInputLayout;

import androidx.databinding.BindingAdapter;

public class CutomBindings {

    @BindingAdapter("app:errorText")
    public static void errorText(TextInputLayout inputLayout, String text) {
        inputLayout.setError((text == null || text.isEmpty()) ? null : text);
    }

}
