package com.storiqa.storiqawallet.ui.dialogs.send

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.databinding.DialogSendConfirmationBinding
import com.storiqa.storiqawallet.di.components.DaggerFragmentComponent
import com.storiqa.storiqawallet.di.components.FragmentComponent
import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import javax.inject.Inject

class SendConfirmationDialog : DialogFragment() {

    companion object {
        const val ARGUMENT_ADDRESS = "argument_address"
        const val ARGUMENT_AMOUNT = "argument_amount"
        const val ARGUMENT_FEE = "argument_fee"
        const val ARGUMENT_TOTAL = "argument_total"

        @JvmStatic
        fun newInstance(
                address: String,
                amount: String,
                fee: String,
                total: String
        ): SendConfirmationDialog {
            return SendConfirmationDialog().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_ADDRESS, address)
                    putString(ARGUMENT_AMOUNT, amount)
                    putString(ARGUMENT_FEE, fee)
                    putString(ARGUMENT_TOTAL, total)
                }
            }
        }
    }

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SendConfirmationViewModel

    private lateinit var onPositiveClick: () -> Unit

    internal val fragmentComponent: FragmentComponent by lazy {
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
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(SendConfirmationViewModel::class.java)
        } catch (e: NoSuchMethodException) {
            throw NoSuchMethodException("You forgot to add \"fun inject(fragment: " +
                    "${this::class.java.simpleName})\" in FragmentComponent")
        }
        isCancelable = false

        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.confirmButtonClicked.observe(this, Observer {
            dismiss()
            onPositiveClick()
        })

        viewModel.cancelButtonClicked.observe(this, Observer {
            dismiss()
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val args = arguments
        if (args != null) {
            viewModel.initData(
                    args.getString(ARGUMENT_ADDRESS) ?: "",
                    args.getString(ARGUMENT_AMOUNT) ?: "",
                    args.getString(ARGUMENT_FEE) ?: "",
                    args.getString(ARGUMENT_TOTAL) ?: ""
            )
        }

        val binding = DialogSendConfirmationBinding.inflate(activity!!.layoutInflater)
        builder.setView(binding.root)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        return builder.create()
    }

    fun setConfirmClickedListener(onClick: () -> Unit) {
        onPositiveClick = onClick
    }
}