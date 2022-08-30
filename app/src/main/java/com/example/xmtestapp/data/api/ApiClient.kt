package com.example.xmtestapp.data.api

import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiInterface: ApiInterface
) {
    suspend fun getQuestions(): List<QuestionDTO>? {
        val response = apiInterface.getQuestions()

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        }

        return null
    }
}