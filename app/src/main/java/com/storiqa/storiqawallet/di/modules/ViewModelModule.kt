package com.storiqa.storiqawallet.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.storiqa.storiqawallet.di.mapkeys.ViewModelKey
import com.storiqa.storiqawallet.ui.dialogs.MessageViewModel
import com.storiqa.storiqawallet.ui.login.LoginViewModel
import com.storiqa.storiqawallet.ui.password.reset.PasswordResetViewModel
import com.storiqa.storiqawallet.ui.password.setup.PasswordSetupViewModel
import com.storiqa.storiqawallet.ui.registration.RegistrationViewModel
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
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindMessageViewModel(messageViewModel: MessageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordResetViewModel::class)
    abstract fun bindPasswordResetViewModel(passwordResetViewModel: PasswordResetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PasswordSetupViewModel::class)
    abstract fun bindPasswordSetupViewModel(passwordSetupViewModel: PasswordSetupViewModel): ViewModel

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