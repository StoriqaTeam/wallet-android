package com.storiqa.storiqawallet.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.FragmentEditProfileBinding
import com.storiqa.storiqawallet.ui.base.BaseFragment
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import com.storiqa.storiqawallet.ui.common.onSubmitButtonClicked

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_edit_profile

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getViewModelClass(): Class<EditProfileViewModel> = EditProfileViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        (activity as IBaseActivity).setupActionBar(binding.toolbar, backButtonEnabled = true)

        initView()

        subscribeEvents()

        return view
    }

    private fun initView() {
        binding.etLastName.setOnEditorActionListener { textView, actionId, event ->
            onSubmitButtonClicked(textView, actionId, event) {
                viewModel.onSaveButtonClicked()
            }
        }

        binding.etFirstName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateNames()
        }

        binding.etLastName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                viewModel.validateNames()
        }
    }

    private fun subscribeEvents() {
        viewModel.changesSaved.observe(this, Observer {
            Toast.makeText(requireContext(), R.string.toast_changes_saved, Toast.LENGTH_SHORT).show()
        })
    }
}