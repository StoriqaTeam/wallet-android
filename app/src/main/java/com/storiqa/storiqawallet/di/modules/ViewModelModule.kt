package com.storiqa.storiqawallet.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.storiqa.storiqawallet.di.mapkeys.ViewModelKey
import com.storiqa.storiqawallet.ui.authorization.AuthorizationViewModel
import com.storiqa.storiqawallet.ui.authorization.signin.SignInViewModel
import com.storiqa.storiqawallet.ui.authorization.signup.SignUpViewModel
import com.storiqa.storiqawallet.ui.dialogs.MessageViewModel
import com.storiqa.storiqawallet.ui.main.MainViewModel
import com.storiqa.storiqawallet.ui.main.exchange.ExchangeViewModel
import com.storiqa.storiqawallet.ui.main.menu.MenuViewModel
import com.storiqa.storiqawallet.ui.main.receive.ReceiveViewModel
import com.storiqa.storiqawallet.ui.main.send.SendViewModel
import com.storiqa.storiqawallet.ui.main.wallet.WalletViewModel
import com.storiqa.storiqawallet.ui.password.reset.PasswordResetViewModel
import com.storiqa.storiqawallet.ui.password.setup.PasswordSetupViewModel
import com.storiqa.storiqawallet.ui.pincode.PinCodeViewModel
import com.storiqa.storiqawallet.ui.question.QuestionViewModel
import com.storiqa.storiqawallet.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    abstract fun bindAuthorizationViewModel(authorizationViewModel: AuthorizationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindMessageViewModel(messageViewModel: MessageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel::class)
    abstract fun bindQuestionViewModel(questionViewModel: QuestionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PinCodeViewModel::class)
    abstract fun bindPinCodeViewModel(messageViewModel: PinCodeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordResetViewModel::class)
    abstract fun bindPasswordResetViewModel(passwordResetViewModel: PasswordResetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordSetupViewModel::class)
    abstract fun bindPasswordSetupViewModel(passwordSetupViewModel: PasswordSetupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindWalletViewModel(walletViewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SendViewModel::class)
    abstract fun bindSendViewModel(sendViewModel: SendViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeViewModel::class)
    abstract fun bindExchangeViewModel(exchangeViewModel: ExchangeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReceiveViewModel::class)
    abstract fun bindReceiveViewModel(receiveViewModel: ReceiveViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    abstract fun bindMenuViewModel(menuViewModel: MenuViewModel): ViewModel

}

class ViewModelFactory
@Inject
constructor(private val creators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}