package com.storiqa.storiqawallet.ui.question

import android.os.Bundle
import com.storiqa.storiqawallet.BR
import com.storiqa.storiqawallet.R
import com.storiqa.storiqawallet.databinding.ActivityQuestionBinding
import com.storiqa.storiqawallet.ui.base.BaseActivity

const val QUESTION_TYPE = "question_type"

class QuestionActivity : BaseActivity<ActivityQuestionBinding, QuestionViewModel>() {

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_question

    override fun getViewModelClass() = QuestionViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val questionType = intent.extras?.get(QUESTION_TYPE) as QuestionType?

        if (questionType == null)
            finish()
        else
            viewModel.setQuestion(questionType)
    }
}