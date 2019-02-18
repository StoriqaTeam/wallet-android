package com.storiqa.storiqawallet.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.databinding.DialogMessageBinding
import com.storiqa.storiqawallet.di.components.DaggerFragmentComponent
import com.storiqa.storiqawallet.di.components.FragmentComponent
import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.ui.base.IBaseActivity
import javax.inject.Inject

class MessageDialog : DialogFragment() {

    companion object {
        const val ARGUMENT_TITLE = "argument_title"
        const val ARGUMENT_MESSAGE = "argument_message"
        const val ARGUMENT_ICON = "argument_icon"
        const val ARGUMENT_POSITIVE_BUTTON = "positive_button"
        const val ARGUMENT_NEGATIVE_BUTTON = "negative_button"

        @JvmStatic
        fun newInstance(title: String, description: String, iconRes: Int) = MessageDialog().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_TITLE, title)
                putString(ARGUMENT_MESSAGE, description)
                putInt(ARGUMENT_ICON, iconRes)
            }
        }
    }

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MessageViewModel

    private lateinit var onPositiveClick: () -> Unit
    private var onNegativeClick: (() -> Unit)? = null

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
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageViewModel::class.java)
        } catch (e: NoSuchMethodException) {
            throw NoSuchMethodException("You forgot to add \"fun inject(fragment: " +
                    "${this::class.java.simpleName})\" in FragmentComponent")
        }
        isCancelable = false

        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.positiveButtonClicked.observe(this, Observer {
            dismiss()
            onPositiveClick.invoke()
        })

        viewModel.negativeButtonClicked.observe(this, Observer {
            dismiss()
            onNegativeClick?.invoke()
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val args = arguments
        if (args != null) {
            val btnNegativeText = if (onNegativeClick == null) null else
                args.getString(ARGUMENT_NEGATIVE_BUTTON)

            viewModel.initData(
                    args.getString(ARGUMENT_TITLE) ?: "",
                    args.getString(ARGUMENT_MESSAGE) ?: "",
                    App.res.getDrawable(args.getInt(ARGUMENT_ICON)),
                    args.getString(ARGUMENT_POSITIVE_BUTTON) ?: "",
                    btnNegativeText
            )
        }

        val binding = DialogMessageBinding.inflate(activity!!.layoutInflater)
        builder.setView(binding.root)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        return builder.create()
    }

    fun setPositiveButton(name: String, onClick: () -> Unit) {
        arguments?.putString(ARGUMENT_POSITIVE_BUTTON, name)
        onPositiveClick = onClick
    }

    fun setNegativeButton(name: String, onClick: () -> Unit) {
        arguments?.putString(ARGUMENT_NEGATIVE_BUTTON, name)
        onNegativeClick = onClick

    }
}