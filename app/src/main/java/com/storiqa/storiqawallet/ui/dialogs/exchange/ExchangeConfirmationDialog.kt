package com.storiqa.storiqawallet.ui.dialogs.exchange

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.databinding.DialogExchangeConfirmationBinding
import com.storiqa.storiqawallet.di.components.DaggerFragmentComponent
import com.storiqa.storiqawallet.di.components.FragmentComponent
import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import javax.inject.Inject

class ExchangeConfirmationDialog : DialogFragment() {

    companion object {
        const val ARGUMENT_REMITTANCE_ACCOUNT = "remittance_account"
        const val ARGUMENT_REMITTANCE_AMOUNT = "remittance_amount"
        const val ARGUMENT_COLLECTION_ACCOUNT = "collection_account"
        const val ARGUMENT_COLLECTION_AMOUNT = "collection_amount"

        @JvmStatic
        fun newInstance(
                remittanceAccount: String,
                remittanceAmount: String,
                collectionAccount: String,
                collectionAmount: String
        ): ExchangeConfirmationDialog {
            return ExchangeConfirmationDialog().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_REMITTANCE_ACCOUNT, remittanceAccount)
                    putString(ARGUMENT_REMITTANCE_AMOUNT, remittanceAmount)
                    putString(ARGUMENT_COLLECTION_ACCOUNT, collectionAccount)
                    putString(ARGUMENT_COLLECTION_AMOUNT, collectionAmount)
                }
            }
        }
    }

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ExchangeConfirmationViewModel

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
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExchangeConfirmationViewModel::class.java)
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
                    args.getString(ARGUMENT_REMITTANCE_ACCOUNT) ?: "",
                    args.getString(ARGUMENT_REMITTANCE_AMOUNT) ?: "",
                    args.getString(ARGUMENT_COLLECTION_ACCOUNT) ?: "",
                    args.getString(ARGUMENT_COLLECTION_AMOUNT) ?: ""
            )
        }

        val binding = DialogExchangeConfirmationBinding.inflate(activity!!.layoutInflater)
        builder.setView(binding.root)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        return builder.create()
    }

    fun setConfirmClickedListener(onClick: () -> Unit) {
        onPositiveClick = onClick
    }
}