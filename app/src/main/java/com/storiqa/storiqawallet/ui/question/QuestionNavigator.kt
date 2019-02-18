package com.storiqa.storiqawallet.ui.question

import android.os.Bundle
import com.storiqa.storiqawallet.ui.base.navigator.BaseNavigator
import com.storiqa.storiqawallet.ui.base.navigator.INavigator

class QuestionNavigator(private val navigator: INavigator) : BaseNavigator(navigator),
        IQuestionNavigator {

    override fun openNextQuestion(questionType: QuestionType) {
        val bundle = Bundle()
        bundle.putSerializable(QUESTION_TYPE, questionType)
        navigator.startActivity(QuestionActivity::class.java, bundle)
    }

    override fun openPinActivity() {
        navigator.startActivity("com.storiqa.storiqawallet.SETUP_PIN")
    }

    override fun closeActivity() {
        navigator.finishActivity()
    }
}