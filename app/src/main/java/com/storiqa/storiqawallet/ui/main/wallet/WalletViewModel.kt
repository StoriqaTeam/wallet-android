package com.storiqa.storiqawallet.ui.main.wallet

import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.ITokenProvider
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IUserRepository
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class WalletViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val userRepository: IUserRepository,
            private val userData: IUserDataStorage,
            private val appData: IAppDataStorage,
            private val tokenProvider: ITokenProvider) : BaseViewModel<IMainNavigator>() {

    lateinit var accounts: Observable<ArrayList<Bill>>

    init {
        setNavigator(navigator)


        val token = appData.token
        if (tokenProvider.isExpired(token))
            tokenProvider.refreshToken({
                appData.token = token
                updateData()
            }, ::handleError)
        else
            updateData()
    }

    private fun updateData() {
        userRepository.updateUser(::handleError)

        //get User from BD

        /*val user = userRepository.getUser(userData.email)

        user.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("TAGGG", "onNext ${it.id}")
                    print(it)
                }, {
                    Log.d("TAGGG", "onError ${it.message}")
                    print(it)
                })*/
    }

}