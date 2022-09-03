package com.example.xmtestapp.domain

import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.data.db.repository.QuestionRepositoryLocal
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal
) {
    suspend fun getQuestionsFromRepo(): List<QuestionEntity> {
        return questionRepositoryLocal.getAll()
    }
}