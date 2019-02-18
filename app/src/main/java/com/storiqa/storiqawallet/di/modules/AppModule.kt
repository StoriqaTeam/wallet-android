package com.storiqa.storiqawallet.di.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.storiqa.cryptokeys.IKeyGenerator
import com.storiqa.cryptokeys.ISigner
import com.storiqa.cryptokeys.KeyGenerator
import com.storiqa.cryptokeys.Signer
import com.storiqa.storiqawallet.data.ITokenProvider
import com.storiqa.storiqawallet.data.TokenProvider
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.polling.IShortPolling
import com.storiqa.storiqawallet.data.polling.ShortPolling
import com.storiqa.storiqawallet.data.preferences.AppDataStorage
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.preferences.UserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.data.repository.ITransactionsRepository
import com.storiqa.storiqawallet.di.qualifiers.AppContext
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.utils.PrefUtil
import com.storiqa.storiqawallet.utils.SignUtil
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {

    @Provides
    @PerApplication
    @AppContext
    internal fun provideAppContext(): Context = app

    @Provides
    @PerApplication
    internal fun provideResources(): Resources = app.resources

    @Provides
    @PerApplication
    internal fun providePrefUtil(): PrefUtil = PrefUtil(app.applicationContext)

    @Provides
    @PerApplication
    internal fun provideUserDataStorage(prefUtil: PrefUtil): IUserDataStorage = UserDataStorage(prefUtil)

    @Provides
    @PerApplication
    internal fun provideAppDataStorage(prefUtil: PrefUtil): IAppDataStorage = AppDataStorage(prefUtil)


    @Provides
    @PerApplication
    internal fun provideShortPolling(accountRepository: IAccountsRepository,
                                     ratesRepository: IRatesRepository,
                                     transactionsRepository: ITransactionsRepository): IShortPolling {
        return ShortPolling(accountRepository, ratesRepository, transactionsRepository)
    }

    @Provides
    @PerApplication
    internal fun provideSigner(): ISigner = Signer()

    @Provides
    @PerApplication
    internal fun provideKeyGenerator(): IKeyGenerator = KeyGenerator()

    @Provides
    @PerApplication
    internal fun provideTokenProvider(appData: IAppDataStorage, signUtil: SignUtil, walletApi: WalletApi):
            ITokenProvider = TokenProvider(appData, signUtil, walletApi)
}