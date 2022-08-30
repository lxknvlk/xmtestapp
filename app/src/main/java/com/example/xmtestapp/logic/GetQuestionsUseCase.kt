package com.example.xmtestapp.logic

import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.data.db.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val apiClient: ApiClient,
    private val questionRepository: QuestionRepository
) {
    suspend fun getQuestionsFromBackend(): List<QuestionEntity>?{
        val questions:  List<QuestionEntity>? = apiClient.getQuestions()

        questions?.let {
            questionRepository.insertAll(questions)
        }

        return questions
    }

    suspend fun getQuestionsFromRepo(): List<QuestionEntity>{
        return questionRepository.getAll()
    }
}