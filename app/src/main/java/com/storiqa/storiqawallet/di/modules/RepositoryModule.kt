package com.storiqa.storiqawallet.di.modules

import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.repository.UserRepository
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.utils.SignUtil
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    @PerApplication
    internal fun provideUserRepository(userDao: UserDao,
                                       walletApi: WalletApi,
                                       appDataStorage: IAppDataStorage,
                                       signUtil: SignUtil): UserRepository {
        return UserRepository(userDao, walletApi, appDataStorage, signUtil)
    }

}