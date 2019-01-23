package com.storiqa.storiqawallet.ui.main.menu

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.di.components.DaggerFragmentComponent
import com.storiqa.storiqawallet.di.components.FragmentComponent
import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import javax.inject.Inject

class MenuFragment : PreferenceFragmentCompat() {

    companion object {
        private const val EDIT_PROFILE_KEY = "edit_profile"
        private const val CHANGE_PASSWORD_KEY = "change_password"
        private const val APP_INFO_KEY = "app_info"
    }

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: IMenuViewModel


    private val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .activityComponent((activity as IBaseActivity).activityComponent)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            FragmentComponent::class.java.getDeclaredMethod("inject", this::class.java)
                    .invoke(fragmentComponent, this)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(MenuViewModel::class.java)
        } catch (e: NoSuchMethodException) {
            throw NoSuchMethodException("You forgot to add \"fun inject(fragment: " +
                    "${this::class.java.simpleName})\" in FragmentComponent")
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            EDIT_PROFILE_KEY -> onEditProfileSelected()
            CHANGE_PASSWORD_KEY -> onChangePasswordSelected()
            APP_INFO_KEY -> onAppInfoSelected()
        }

        return super.onPreferenceTreeClick(preference)
    }

    private fun onEditProfileSelected() {
        viewModel.onEditProfileSelected()
    }

    private fun onChangePasswordSelected() {
        viewModel.onChangePasswordSelected()
    }

    private fun onAppInfoSelected() {
        viewModel.onAppInfoSelected()
    }

}
