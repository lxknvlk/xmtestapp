package com.example.xmtestapp.domain.interfaces

interface DownloadQuestionsUseCase {
    suspend fun downloadQuestions(): Boolean
}