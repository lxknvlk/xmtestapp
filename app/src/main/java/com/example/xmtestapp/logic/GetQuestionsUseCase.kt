package com.example.xmtestapp.logic

import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.entity.QuestionDTO
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val apiClient: ApiClient
) {
    suspend fun getQuestions(): List<QuestionDTO>?{
        val questions:  List<QuestionDTO>? = apiClient.getQuestions()

        return questions
    }
}