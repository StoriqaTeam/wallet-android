package com.storiqa.storiqawallet.di.components

import com.storiqa.storiqawallet.di.modules.FragmentModule
import com.storiqa.storiqawallet.di.modules.ViewModelModule
import com.storiqa.storiqawallet.di.scopes.PerFragment
import com.storiqa.storiqawallet.ui.authorization.signin.SignInFragment
import com.storiqa.storiqawallet.ui.authorization.signup.SignUpFragment
import com.storiqa.storiqawallet.ui.dialogs.exchange.ExchangeConfirmationDialog
import com.storiqa.storiqawallet.ui.dialogs.message.MessageDialog
import com.storiqa.storiqawallet.ui.dialogs.send.SendConfirmationDialog
import com.storiqa.storiqawallet.ui.main.account.AccountFragment
import com.storiqa.storiqawallet.ui.main.details.TransactionDetailsFragment
import com.storiqa.storiqawallet.ui.main.exchange.ExchangeFragment
import com.storiqa.storiqawallet.ui.main.menu.MenuFragment
import com.storiqa.storiqawallet.ui.main.receive.ReceiveFragment
import com.storiqa.storiqawallet.ui.main.send.SendFragment
import com.storiqa.storiqawallet.ui.main.transactions.TransactionsFragment
import com.storiqa.storiqawallet.ui.main.wallet.WalletFragment
import com.storiqa.storiqawallet.ui.password.reset.PasswordResetFragment
import com.storiqa.storiqawallet.ui.password.setup.PasswordSetupFragment
import dagger.Component

@PerFragment
@Component(dependencies = [ActivityComponent::class],
        modules = [FragmentModule::class, ViewModelModule::class])
interface FragmentComponent : FragmentComponentProvides {

    fun inject(fragment: MessageDialog)
    fun inject(fragment: SendConfirmationDialog)
    fun inject(fragment: ExchangeConfirmationDialog)

    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)

    fun inject(fragment: PasswordResetFragment)
    fun inject(fragment: PasswordSetupFragment)

    fun inject(fragment: WalletFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: TransactionsFragment)
    fun inject(fragment: TransactionDetailsFragment)
    fun inject(fragment: SendFragment)
    fun inject(fragment: ExchangeFragment)
    fun inject(fragment: ReceiveFragment)
    fun inject(fragment: MenuFragment)
}

interface FragmentComponentProvides : ActivityComponentProvides {

}