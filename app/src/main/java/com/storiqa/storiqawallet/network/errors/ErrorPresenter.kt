package com.storiqa.storiqawallet.network.errors

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.storiqa.storiqawallet.R

sealed class ErrorPresenter

open class ErrorPresenterDialog(
        val dialogType: DialogType = DialogType.NO_INTERNET,
        @StringRes val title: Int = 0, //TODO delete default params
        @StringRes val description: Int = 0,
        @DrawableRes val icon: Int = 0,
        val positiveButton: DialogButton? = null,
        val negativeButton: DialogButton? = null) : ErrorPresenter()

data class ErrorPresenterFields(
        val fieldErrors: ArrayList<HashMap<String, Int>>) : ErrorPresenter()

enum class DialogType {
    NO_INTERNET, DEVICE_NOT_ATTACHED, RECOVERY_PASS_MAIL_SENT, RECOVERY_PASS_SET_UP,
    REGISTRATION_MAIL_SENT, EMAIL_NOT_VERIFIED
}

data class DialogButton(
        @StringRes val name: Int,
        var onClick: () -> Unit)

class PassMailSentDialogPresenter : ErrorPresenterDialog(
        DialogType.RECOVERY_PASS_MAIL_SENT,
        R.string.dialog_pass_mail_sent_title,
        R.string.dialog_pass_mail_sent_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class PassSetUpDialogPresenter : ErrorPresenterDialog(
        DialogType.RECOVERY_PASS_SET_UP,
        R.string.dialog_pass_set_up_title,
        R.string.dialog_pass_set_up_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_sign_in, {}))

class RegistrationMailSentDialogPresenter : ErrorPresenterDialog(
        DialogType.REGISTRATION_MAIL_SENT,
        R.string.dialog_registration_mail_sent_title,
        R.string.dialog_registration_mail_sent_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_sign_in, {}))

class EmailNotVerifiedDialogPresenter : ErrorPresenterDialog(
        DialogType.EMAIL_NOT_VERIFIED,
        R.string.dialog_not_verified_title,
        R.string.dialog_not_verified_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_resend, {}),
        DialogButton(R.string.button_cancel, {}))