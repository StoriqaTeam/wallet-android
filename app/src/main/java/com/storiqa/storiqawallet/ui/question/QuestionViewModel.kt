package com.storiqa.storiqawallet.ui.question

import androidx.databinding.ObservableInt
import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.common.NonNullObservableField
import com.storiqa.storiqawallet.ui.base.BaseViewModel
import javax.inject.Inject

class QuestionViewModel
@Inject
constructor(navigator: IQuestionNavigator) : BaseViewModel<IQuestionNavigator>() {

    val title = NonNullObservableField("")
    val description = NonNullObservableField("")
    val positiveButtonText = NonNullObservableField("")
    val negativeButtonText = NonNullObservableField("")
    var icon = ObservableInt(R.drawable.general_error_icon)

    private lateinit var questionType: QuestionType

    init {
        setNavigator(navigator)
    }

    fun setQuestion(type: QuestionType) {
        questionType = type
        when (questionType) {
            QuestionType.SET_UP_QUICK_LAUNCH -> initQuickLaunch()
            QuestionType.SET_UP_PIN_CODE -> initPinCode()
            QuestionType.SET_UP_FINGERPRINT -> initFingerprint()
        }
    }

    private fun initQuickLaunch() {
        title.set(App.res.getString(R.string.text_quick_launch))
        description.set(App.res.getString(R.string.text_quick_launch_description))
        positiveButtonText.set(App.res.getString(R.string.button_set_up_quick_launch))
        icon.set(R.drawable.quick_launch_image)
    }

    private fun initPinCode() {
        title.set(App.res.getString(R.string.text_pin_code))
        description.set(App.res.getString(R.string.text_quick_launch_description))
        positiveButtonText.set(App.res.getString(R.string.button_set_up_quick_launch))
        icon.set(R.drawable.set_pin_image)
    }

    private fun initFingerprint() {
        title.set(App.res.getString(R.string.text_fingerprint))
        positiveButtonText.set(App.res.getString(R.string.button_set_up_fingerprint))
        negativeButtonText.set(App.res.getString(R.string.button_do_not_use))
        icon.set(R.drawable.set_fingerprint)
    }

    fun onPositiveButtonClicked() {
        when (questionType) {
            QuestionType.SET_UP_QUICK_LAUNCH -> getNavigator()?.openNextQuestion(QuestionType.SET_UP_PIN_CODE)
            QuestionType.SET_UP_PIN_CODE -> getNavigator()?.openPinActivity()
            QuestionType.SET_UP_FINGERPRINT -> initFingerprint()
        }
    }

    fun onNegativeButtonClicked() {
        getNavigator()?.closeActivity()
    }

}