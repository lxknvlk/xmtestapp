package com.example.xmtestapp.data.api

import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.api.entity.QuestionEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("questions")
    suspend fun getQuestions(): Response<List<QuestionEntity>>

    @POST("question/submit")
    suspend fun submitAnswer(@Body answerEntity: AnswerEntity): Response<Unit>
}