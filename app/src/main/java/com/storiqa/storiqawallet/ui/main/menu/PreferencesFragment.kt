package com.storiqa.storiqawallet.ui.main.menu

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.storiqa.storiqawallet.R

class PreferencesFragment : PreferenceFragmentCompat() {

    companion object {
        private const val EDIT_PROFILE_KEY = "edit_profile"
        private const val CHANGE_PASSWORD_KEY = "change_password"
        private const val APP_INFO_KEY = "app_info"
    }

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(requireParentFragment()).get(MenuViewModel::class.java)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            EDIT_PROFILE_KEY -> viewModel.onEditProfileClicked()
            CHANGE_PASSWORD_KEY -> viewModel.onChangePasswordClicked()
            APP_INFO_KEY -> viewModel.onAppInfoClicked()
        }

        return super.onPreferenceTreeClick(preference)
    }

}
