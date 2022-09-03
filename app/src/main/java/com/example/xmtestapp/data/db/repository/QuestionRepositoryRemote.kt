package com.example.xmtestapp.data.db.repository

import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.api.entity.QuestionEntity
import javax.inject.Inject

class QuestionRepositoryRemote @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun getQuestions(): List<QuestionEntity>? {
        return apiClient.getQuestions()
    }

    suspend fun submitAnswer(answerEntity: AnswerEntity): Boolean{
        return apiClient.submitAnswer(answerEntity)
    }
}
