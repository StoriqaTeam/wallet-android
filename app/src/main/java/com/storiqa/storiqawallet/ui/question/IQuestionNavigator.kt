package com.storiqa.storiqawallet.ui.question

import com.storiqa.storiqawallet.ui.base.navigator.IBaseNavigator

interface IQuestionNavigator : IBaseNavigator {

    fun openNextQuestion(questionType: QuestionType)

    fun openPinActivity()

    fun closeActivity()

}