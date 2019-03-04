package com.storiqa.storiqawallet.ui.main.wallet

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.data.repository.IAccountsRepository
import com.storiqa.storiqawallet.data.repository.IRatesRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.ui.main.account.AccountFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class WalletViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val accountsRepository: IAccountsRepository,
            private val ratesRepository: IRatesRepository,
            private val userData: IUserDataStorage) : BaseViewModel<IMainNavigator>() {

    val updateAccounts = SingleLiveEvent<List<Account>>()

    var accounts = MutableLiveData<List<Account>>()

    init {
        setNavigator(navigator)

        accountsRepository
                .getAccounts(userData.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { accounts.value = it.reversed() }
    }

    fun onAccountClicked(position: Int, element: View, transition: String) {
        val bundle = Bundle()
        bundle.putInt(AccountFragment.KEY_POSITION, position)
        getNavigator()?.showAccountFragment(bundle, element, transition)
    }

}