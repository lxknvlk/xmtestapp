package com.example.xmtestapp.data.db.repository

import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.interfaces.QuestionRepositoryRemote
import com.example.xmtestapp.domain.entity.Answer
import com.example.xmtestapp.domain.entity.Question
import javax.inject.Inject

class QuestionRepositoryRemoteImpl @Inject constructor(
    private val apiClient: ApiClient
) : QuestionRepositoryRemote {
    override suspend fun getQuestions(): List<Question> {
        return apiClient.getQuestions().map { it.toQuestion() }
    }

    override suspend fun submitAnswer(answerEntity: Answer): Boolean {
        return apiClient.submitAnswer(AnswerEntity.fromAnswer(answerEntity))
    }
}
