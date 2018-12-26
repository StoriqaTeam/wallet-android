package com.storiqa.storiqawallet.di.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.storiqa.cryptokeys.IKeyGenerator
import com.storiqa.cryptokeys.ISigner
import com.storiqa.cryptokeys.KeyGenerator
import com.storiqa.cryptokeys.Signer
import com.storiqa.storiqawallet.data.AppDataStorage
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.data.UserDataStorage
import com.storiqa.storiqawallet.di.qualifiers.AppContext
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.utils.PrefUtil
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
    internal fun provideSigner(): ISigner = Signer()

    @Provides
    @PerApplication
    internal fun provideKeyGenerator(): IKeyGenerator = KeyGenerator()
}