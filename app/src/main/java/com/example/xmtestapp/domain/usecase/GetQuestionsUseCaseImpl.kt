package com.example.xmtestapp.domain.usecase

import com.example.xmtestapp.data.interfaces.QuestionRepositoryLocal
import com.example.xmtestapp.domain.entity.Question
import com.example.xmtestapp.domain.interfaces.GetQuestionsUseCase
import javax.inject.Inject

class GetQuestionsUseCaseImpl @Inject constructor(
    private val questionRepositoryLocal: QuestionRepositoryLocal
) : GetQuestionsUseCase {
    override suspend fun getQuestionsFromRepo(): List<Question> {
        return questionRepositoryLocal.getAll()
    }
}