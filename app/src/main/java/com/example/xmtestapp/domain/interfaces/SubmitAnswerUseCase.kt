package com.example.xmtestapp.domain.interfaces

interface SubmitAnswerUseCase {
    suspend fun submitAnswer(id: Int, answer: String): Boolean
}