package com.example.xmtestapp.data.api

import retrofit2.Response

class ApiClient {

    private val apiInterface: ApiInterface =
        RetrofitClient().getInstance().create(ApiInterface::class.java)

    suspend fun getQuestions(): List<QuestionDTO>? {
        val response = apiInterface.getQuestions()

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        }

        return null
    }
}