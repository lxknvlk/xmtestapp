package com.example.xmtestapp.domain

import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.db.repository.QuestionRepositoryLocal
import com.example.xmtestapp.data.db.repository.QuestionRepositoryRemote
import javax.inject.Inject

class SubmitAnswerUseCase @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal,
    private val questionRepositoryRemote: QuestionRepositoryRemote,
) {

    suspend fun submitAnswer(id: Int, answer: String): Boolean {
        val answerEntity = AnswerEntity(id, answer)
        val isSuccess = questionRepositoryRemote.submitAnswer(answerEntity)

        if (isSuccess) {
            questionRepositoryLocal.updateAnswer(id, answer)
        }

        return isSuccess
    }
}