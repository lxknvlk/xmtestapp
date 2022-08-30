package com.example.xmtestapp.data.api.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "question")
data class QuestionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "question")
    @SerializedName("question")
    val question: String,

    @ColumnInfo(name = "answer")
    val answer: String?
)
