package com.storiqa.storiqawallet.ui.main.menu

import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.data.db.AppDatabase
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.dialogs.message.DialogType
import com.storiqa.storiqawallet.ui.dialogs.message.SignOutDialogPresenter
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MenuViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val appDatabase: AppDatabase,
            private val appDataStorage: IAppDataStorage
) : BaseViewModel<IMainNavigator>() {

    val errorToast = SingleLiveEvent<Boolean>()

    init {
        setNavigator(navigator)
    }

    fun onEditProfileClicked() {
        getNavigator()?.showEditProfile()
    }

    fun onChangePasswordClicked() {
        getNavigator()?.showChangePassword()
    }

    fun onAppInfoClicked() {
        getNavigator()?.showAppInfo()
    }

    fun onSignOutButtonClicked() {
        showMessageDialog(SignOutDialogPresenter())
    }

    override fun getDialogPositiveButtonClicked(dialogType: DialogType, params: HashMap<String, String>?): () -> Unit {
        if (dialogType == DialogType.SIGN_OUT)
            return {

                Completable.fromAction { appDatabase.clearAllTables() }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            appDataStorage.token = ""
                            appDataStorage.currentUserEmail = ""
                            appDataStorage.isPinEntered = false
                            appDataStorage.oldestPendingTransactionTime = 0
                            getNavigator()?.showSignInFragment()
                        }, {
                            errorToast.trigger()
                        })
            }
        else return {}
    }
}