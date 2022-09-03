package com.example.xmtestapp.data.api

import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.api.entity.QuestionEntity
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiInterface: ApiInterface
) {
    suspend fun getQuestions(): List<QuestionEntity> {
        try {
            val response = apiInterface.getQuestions()

            if (response.isSuccessful && response.body() != null) {
                return response.body()!!
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return listOf()
    }

    suspend fun submitAnswer(answerEntity: AnswerEntity): Boolean {
        val response = apiInterface.submitAnswer(answerEntity)
        return response.isSuccessful
    }
}