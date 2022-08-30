package com.example.xmtestapp.data.api

import com.google.gson.annotations.SerializedName

data class QuestionDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val question: String
)
