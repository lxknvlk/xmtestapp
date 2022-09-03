package com.example.xmtestapp.domain.usecase

import com.example.xmtestapp.data.interfaces.QuestionRepositoryLocal
import com.example.xmtestapp.data.interfaces.QuestionRepositoryRemote
import com.example.xmtestapp.domain.entity.Question
import com.example.xmtestapp.domain.interfaces.DownloadQuestionsUseCase
import javax.inject.Inject

class DownloadQuestionsUseCaseImpl @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal,
    private val questionRepositoryRemote: QuestionRepositoryRemote,
) : DownloadQuestionsUseCase {
    override suspend fun downloadQuestions(): Boolean {
        val questions: List<Question> = questionRepositoryRemote.getQuestions()
        questionRepositoryLocal.insertAll(questions)
        return questions.isNotEmpty()
    }
}