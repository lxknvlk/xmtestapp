package com.example.xmtestapp.data.api.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.xmtestapp.domain.entity.Answer
import com.google.gson.annotations.SerializedName

@Entity(tableName = "answer")
data class AnswerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "answer")
    @SerializedName("answer")
    val answer: String
) {
    companion object {
        fun fromAnswer(answer: Answer): AnswerEntity {
            return AnswerEntity(id = answer.id, answer = answer.answer)
        }
    }

    fun toAnswer(): Answer {
        return Answer(id = id, answer = answer)
    }
}