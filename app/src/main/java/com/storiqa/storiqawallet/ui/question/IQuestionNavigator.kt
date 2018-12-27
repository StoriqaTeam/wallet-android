package com.storiqa.storiqawallet.ui.question

interface IQuestionNavigator {

    fun openNextQuestion(questionType: QuestionType)

    fun openPinActivity()

    fun closeActivity()

}