package com.storiqa.storiqawallet.ui.main.profile

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.common.SingleLiveEvent
import com.storiqa.storiqawallet.common.addOnPropertyChanged
import com.storiqa.storiqawallet.data.repository.IUserRepository
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import com.storiqa.storiqawallet.ui.main.IMainNavigator
import com.storiqa.storiqawallet.utils.isUserNameValid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditProfileViewModel
@Inject
constructor(navigator: IMainNavigator,
            private val userRepository: IUserRepository
) : BaseViewModel<IMainNavigator>() {

    val changesSaved = SingleLiveEvent<Boolean>()

    val firstName = NonNullObservableField("")
    val lastName = NonNullObservableField("")
    val firstNameError = NonNullObservableField("")
    val lastNameError = NonNullObservableField("")

    init {
        setNavigator(navigator)
        userRepository
                .getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    firstName.set(it.firstName)
                    lastName.set(it.lastName)
                }
        firstName.addOnPropertyChanged {
            firstNameError.set("")
        }
        lastName.addOnPropertyChanged {
            lastNameError.set("")
        }
    }

    @SuppressLint("CheckResult")
    fun onSaveButtonClicked() {
        hideKeyboard()
        if (!validateNames())
            return

        showLoadingDialog()
        userRepository
                .updateUserProfile(firstName.get(), lastName.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    hideLoadingDialog()
                    changesSaved.trigger()
                }, {
                    hideLoadingDialog()
                    handleError(it as Exception)
                })
    }

    fun validateNames(): Boolean {
        var isNamesValid = true
        if (!isUserNameValid(firstName.get())) {
            firstNameError.set(App.res.getString(R.string.error_name_not_valid))
            isNamesValid = false
        }

        if (!isUserNameValid(lastName.get())) {
            lastNameError.set(App.res.getString(R.string.error_name_not_valid))
            isNamesValid = false
        }

        return isNamesValid
    }
}