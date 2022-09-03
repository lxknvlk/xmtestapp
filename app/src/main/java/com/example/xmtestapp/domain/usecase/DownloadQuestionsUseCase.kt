package com.example.xmtestapp.domain.usecase

import com.example.xmtestapp.data.db.repository.QuestionRepositoryLocal
import com.example.xmtestapp.data.db.repository.QuestionRepositoryRemote
import com.example.xmtestapp.domain.entity.Question
import javax.inject.Inject

class DownloadQuestionsUseCase @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal,
    private val questionRepositoryRemote: QuestionRepositoryRemote,
) {

    suspend fun downloadQuestions(): Boolean {
        val questions: List<Question> = questionRepositoryRemote.getQuestions()
        questionRepositoryLocal.insertAll(questions)
        return questions.isNotEmpty()
    }
}