package com.example.xmtestapp.domain.usecase

import com.example.xmtestapp.data.db.repository.QuestionRepositoryLocal
import com.example.xmtestapp.domain.entity.Question
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal
) {
    suspend fun getQuestionsFromRepo(): List<Question> {
        return questionRepositoryLocal.getAll()
    }
}