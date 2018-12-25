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
        val negativeButton: DialogButton? = null,
        var params: HashMap<String, String>? = null) : ErrorPresenter()

data class ErrorPresenterFields(
        val fieldErrors: ArrayList<HashMap<String, Int>>) : ErrorPresenter()

enum class DialogType {
    NO_INTERNET, DEVICE_NOT_ATTACHED, RECOVERY_PASS_MAIL_SENT, RECOVERY_PASS_SET_UP,
    REGISTRATION_MAIL_SENT, EMAIL_NOT_VERIFIED, ATTACH_DEVICE_MAIL_SENT, UNKNOWN_ERROR,
    EMAIL_TIMEOUT, DEVICE_ATTACHED
}

data class DialogButton(
        @StringRes val name: Int,
        var onClick: () -> Unit)

class EmailTimeoutDialogPresenter : ErrorPresenterDialog(
        DialogType.EMAIL_TIMEOUT,
        R.string.dialog_email_timeout_title,
        R.string.dialog_email_timeout_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_resend, {}),
        DialogButton(R.string.cancel, {}))

class NoInternetDialogPresenter : ErrorPresenterDialog(
        DialogType.NO_INTERNET,
        R.string.dialog_no_internet_title,
        R.string.dialog_no_internet_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class UnknownErrorDialogPresenter : ErrorPresenterDialog(
        DialogType.UNKNOWN_ERROR,
        R.string.dialog_unknown_error_title,
        R.string.dialog_unknown_error_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

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

class NotAttachedDialogPresenter : ErrorPresenterDialog(
        DialogType.DEVICE_NOT_ATTACHED,
        R.string.dialog_device_not_attached_title,
        R.string.dialog_device_not_attached_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}),
        DialogButton(R.string.cancel, {}))

class DeviceAttachedDialogPresenter : ErrorPresenterDialog(
        DialogType.DEVICE_ATTACHED,
        R.string.dialog_device_attached_title,
        R.string.dialog_device_attached_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class AttachDeviceMailSentDialogPresenter : ErrorPresenterDialog(
        DialogType.ATTACH_DEVICE_MAIL_SENT,
        R.string.dialog_attach_device_mail_sent_title,
        R.string.dialog_attach_device_mail_sent_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))