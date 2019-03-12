package com.storiqa.storiqawallet.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.storiqa.storiqawallet.data.network.errors.TokenExpired
import com.storiqa.storiqawallet.data.polling.IShortPolling
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.dialogs.message.DialogType
import com.storiqa.storiqawallet.ui.main.MainViewState.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val appData: IAppDataStorage,
            private val userData: IUserDataStorage,
            private val shortPolling: IShortPolling) : BaseViewModel<IMainNavigator>() {

    var viewState = MutableLiveData(LOADING)

    private var shortPoller: Disposable? = null

    @SuppressLint("CheckResult")
    fun onStart() {
        shortPoller?.dispose()
        val id = userData.id
        val email = userData.email
        shortPoller = shortPolling.start(id, email)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    if (it is TokenExpired)
                        onError(it)
                    else
                        onInfoUpdated()
                }
                .retryWhen { it.delay(30, TimeUnit.SECONDS) }
                .subscribe {
                    if (it)
                        onInfoUpdated()
                }
    }

    fun onStop() {
        shortPoller?.dispose()
    }

    private fun onInfoUpdated() {
        if (viewState.value != CONTENT)
            viewState.value = CONTENT
    }

    private fun onError(error: Exception) {
        if (viewState.value == LOADING)
            viewState.value = STUB
        handleError(error)
    }

    fun onMenuItemSelected(position: Int, wasSelected: Boolean): Boolean {
        if (!wasSelected)
            when (position) {
                0 -> getNavigator()?.showWalletFragment()
                1 -> getNavigator()?.showSendFragment()
                2 -> getNavigator()?.showExchangeFragment()
                3 -> getNavigator()?.showReceiveFragment()
                4 -> getNavigator()?.showMenuFragment()
            }

        return true
    }

    override fun getDialogPositiveButtonClicked(dialogType: DialogType, params: HashMap<String, String>?): () -> Unit {
        when (dialogType) {
            DialogType.TOKEN_EXPIRED -> return {
                appData.isPinEntered = false
                getNavigator()?.showSignInFragment()
            }
            else -> return {}
        }
    }
}