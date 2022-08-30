package com.example.xmtestapp.data.api

import com.example.xmtestapp.data.api.entity.AnswerDTO
import com.example.xmtestapp.data.api.entity.QuestionDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("questions")
    suspend fun getQuestions(): Response<List<QuestionDTO>>

    @POST("question/submit")
    suspend fun submitQuestion(answerDTO: AnswerDTO): Response<Any>
}