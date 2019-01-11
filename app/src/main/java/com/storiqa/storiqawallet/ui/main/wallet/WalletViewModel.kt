package com.storiqa.storiqawallet.ui.main.wallet

import android.util.Log
import com.storiqa.storiqawallet.data.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IUserRepository
import com.storiqa.storiqawallet.objects.Bill
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class WalletViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val userRepository: IUserRepository,
            private val userData: IUserDataStorage) : BaseViewModel<IMainNavigator>() {

    lateinit var accounts: Observable<ArrayList<Bill>>

    init {
        setNavigator(navigator)

        userRepository.updateUser()
        val user = userRepository.getUser(userData.email)

        user.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("TAGGG", "onNext ${it.id}")
                    print(it)
                }, {
                    Log.d("TAGGG", "onError ${it.message}")
                    print(it)
                })
    }

}