package com.example.xmtestapp.domain

import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.data.db.repository.QuestionRepository
import javax.inject.Inject

class SurveyUseCase @Inject constructor(
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

    suspend fun submitAnswer(id: Int, answer: String): Boolean{
        val answerEntity = AnswerEntity(id, answer)
        val success = apiClient.submitAnswer(answerEntity)

        if (success){
            questionRepository.updateAnswer(id, answer)
        }

        return success
    }

    suspend fun getQuestionsFromRepo(): List<QuestionEntity>{
        return questionRepository.getAll()
    }
}