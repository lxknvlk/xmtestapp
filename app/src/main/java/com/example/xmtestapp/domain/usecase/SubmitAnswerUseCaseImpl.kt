package com.example.xmtestapp.domain.usecase

import com.example.xmtestapp.data.interfaces.QuestionRepositoryLocal
import com.example.xmtestapp.data.interfaces.QuestionRepositoryRemote
import com.example.xmtestapp.domain.entity.Answer
import com.example.xmtestapp.domain.interfaces.SubmitAnswerUseCase
import javax.inject.Inject

class SubmitAnswerUseCaseImpl @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal,
    private val questionRepositoryRemote: QuestionRepositoryRemote,
) : SubmitAnswerUseCase {

    override suspend fun submitAnswer(id: Int, answer: String): Boolean {
        val answerObject = Answer(id, answer)
        val isSuccess = questionRepositoryRemote.submitAnswer(answerObject)

        if (isSuccess) {
            questionRepositoryLocal.updateAnswer(id, answer)
        }

        return isSuccess
    }
}