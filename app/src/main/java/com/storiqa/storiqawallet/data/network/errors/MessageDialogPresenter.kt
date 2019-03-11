package com.storiqa.storiqawallet.data.network.errors

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.storiqa.storiqawallet.R

sealed class ErrorPresenter

open class MessageDialogPresenter(
        val dialogType: DialogType = DialogType.NO_INTERNET,
        @StringRes val title: Int = 0, //TODO delete default params
        @StringRes val description: Int = 0,
        @DrawableRes val icon: Int = 0,
        val positiveButton: DialogButton? = null,
        val negativeButton: DialogButton? = null,
        var params: HashMap<String, String>? = null) : ErrorPresenter()

data class ErrorPresenterFields(
        val fieldErrors: ArrayList<HashMap<String, String>>) : ErrorPresenter()

enum class DialogType {
    NO_INTERNET, DEVICE_NOT_ATTACHED, RECOVERY_PASS_MAIL_SENT, RECOVERY_PASS_SET_UP,
    REGISTRATION_MAIL_SENT, EMAIL_NOT_VERIFIED, ATTACH_DEVICE_MAIL_SENT, UNKNOWN_ERROR,
    EMAIL_TIMEOUT, DEVICE_ATTACHED, RESET_PIN, WRONG_DEVICE_ID, EMAIL_NOT_PROVIDED, TOKEN_EXPIRED,
    EXIT, PASSWORD_CHANGED
}

data class DialogButton(
        @StringRes val name: Int,
        var onClick: () -> Unit)

class ResetPinDialogPresenter : MessageDialogPresenter(
        DialogType.RESET_PIN,
        R.string.dialog_reset_pin_title,
        R.string.dialog_reset_pin_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_reset_pin, {}),
        DialogButton(R.string.button_cancel, {}))

class EmailTimeoutDialogPresenter : MessageDialogPresenter(
        DialogType.EMAIL_TIMEOUT,
        R.string.dialog_email_timeout_title,
        R.string.dialog_email_timeout_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_resend, {}),
        DialogButton(R.string.button_cancel, {}))

class NoInternetDialogPresenter : MessageDialogPresenter(
        DialogType.NO_INTERNET,
        R.string.dialog_no_internet_title,
        R.string.dialog_no_internet_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class UnknownErrorDialogPresenter : MessageDialogPresenter(
        DialogType.UNKNOWN_ERROR,
        R.string.dialog_unknown_error_title,
        R.string.dialog_unknown_error_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class PassMailSentDialogPresenter : MessageDialogPresenter(
        DialogType.RECOVERY_PASS_MAIL_SENT,
        R.string.dialog_pass_mail_sent_title,
        R.string.dialog_pass_mail_sent_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class PassSetUpDialogPresenter : MessageDialogPresenter(
        DialogType.RECOVERY_PASS_SET_UP,
        R.string.dialog_pass_set_up_title,
        R.string.dialog_pass_set_up_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_sign_in, {}))

class RegistrationMailSentDialogPresenter : MessageDialogPresenter(
        DialogType.REGISTRATION_MAIL_SENT,
        R.string.dialog_registration_mail_sent_title,
        R.string.dialog_registration_mail_sent_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_sign_in, {}))

class EmailNotVerifiedDialogPresenter : MessageDialogPresenter(
        DialogType.EMAIL_NOT_VERIFIED,
        R.string.dialog_not_verified_title,
        R.string.dialog_not_verified_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_resend, {}),
        DialogButton(R.string.button_cancel, {}))

class NotAttachedDialogPresenter : MessageDialogPresenter(
        DialogType.DEVICE_NOT_ATTACHED,
        R.string.dialog_device_not_attached_title,
        R.string.dialog_device_not_attached_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}),
        DialogButton(R.string.button_cancel, {}))

class DeviceAttachedDialogPresenter : MessageDialogPresenter(
        DialogType.DEVICE_ATTACHED,
        R.string.dialog_device_attached_title,
        R.string.dialog_device_attached_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class AttachDeviceMailSentDialogPresenter : MessageDialogPresenter(
        DialogType.ATTACH_DEVICE_MAIL_SENT,
        R.string.dialog_attach_device_mail_sent_title,
        R.string.dialog_attach_device_mail_sent_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class WrongDeviceIdDialogPresenter : MessageDialogPresenter(
        DialogType.WRONG_DEVICE_ID,
        R.string.dialog_wrong_device_id_title,
        R.string.dialog_wrong_device_id_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class EmailNotProvidedDialogPresenter : MessageDialogPresenter(
        DialogType.EMAIL_NOT_PROVIDED,
        R.string.dialog_email_not_provided_title,
        R.string.dialog_email_not_provided_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class TokenExpiredDialogPresenter : MessageDialogPresenter(
        DialogType.TOKEN_EXPIRED,
        R.string.dialog_token_expired_title,
        R.string.dialog_token_expired_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))

class ExitDialogPresenter : MessageDialogPresenter(
        DialogType.EXIT,
        R.string.dialog_exit_title,
        R.string.dialog_exit_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_exit, {}),
        DialogButton(R.string.button_cancel, {}))

class PasswordChangedDialogPresenter : MessageDialogPresenter(
        DialogType.PASSWORD_CHANGED,
        R.string.dialog_password_changed_title,
        R.string.dialog_password_changed_description,
        R.drawable.general_error_icon,
        DialogButton(R.string.button_ok, {}))