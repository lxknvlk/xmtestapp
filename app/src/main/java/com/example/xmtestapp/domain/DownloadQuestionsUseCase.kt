package com.example.xmtestapp.domain

import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.data.db.repository.QuestionRepositoryLocal
import com.example.xmtestapp.data.db.repository.QuestionRepositoryRemote
import javax.inject.Inject

class DownloadQuestionsUseCase @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal,
    private val questionRepositoryRemote: QuestionRepositoryRemote,
) {

    suspend fun downloadQuestions(): Boolean {
        val questions: List<QuestionEntity>? = questionRepositoryRemote.getQuestions()

        return if (questions != null && questions.isNotEmpty()) {
            questionRepositoryLocal.insertAll(questions)
            true
        } else {
            false
        }
    }
}