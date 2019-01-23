package com.storiqa.storiqawallet.ui.main.menu

import android.os.Bundle
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceFragmentCompat
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R

class MenuFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}