package com.example.xmtestapp.domain.usecase

import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.db.repository.QuestionRepositoryLocal
import com.example.xmtestapp.data.db.repository.QuestionRepositoryRemote
import com.example.xmtestapp.domain.entity.Answer
import javax.inject.Inject

class SubmitAnswerUseCase @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal,
    private val questionRepositoryRemote: QuestionRepositoryRemote,
) {

    suspend fun submitAnswer(id: Int, answer: String): Boolean {
        val answerObject = Answer(id, answer)
        val isSuccess = questionRepositoryRemote.submitAnswer(answerObject)

        if (isSuccess) {
            questionRepositoryLocal.updateAnswer(id, answer)
        }

        return isSuccess
    }
}