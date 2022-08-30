package com.example.xmtestapp.data.api

import com.google.gson.annotations.SerializedName

data class AnswerDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val answer: String
)