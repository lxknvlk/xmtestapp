package com.example.xmtestapp.data.interfaces

import com.example.xmtestapp.domain.entity.Answer
import com.example.xmtestapp.domain.entity.Question

interface QuestionRepositoryRemote {
    suspend fun getQuestions(): List<Question>
    suspend fun submitAnswer(answerEntity: Answer): Boolean
}