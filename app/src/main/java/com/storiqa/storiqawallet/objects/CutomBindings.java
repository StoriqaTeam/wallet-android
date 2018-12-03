package com.storiqa.storiqawallet.objects;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;

public class CutomBindings {

    @BindingAdapter("app:errorText")
    public static void errorText(TextInputLayout inputLayout, String text) {
        inputLayout.setError((text == null || text.isEmpty()) ? null : text);
    }

}
