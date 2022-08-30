package com.example.xmtestapp.logic

import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.db.repository.AnswerRepository
import com.example.xmtestapp.data.db.repository.QuestionRepository
import javax.inject.Inject

class SubmitAnswerUseCase @Inject constructor(
    private val apiClient: ApiClient,
    private val answerRepository: AnswerRepository
) {
    suspend fun submitAnswer(id: Int, answer: String): Boolean{
        val answerEntity = AnswerEntity(id, answer)
        val success = apiClient.submitAnswer(answerEntity)

        if (success){
            answerRepository.insert(answerEntity)
        }

        return success
    }

    suspend fun getAnsweredQuestions(): List<AnswerEntity>{
        return answerRepository.getAll()
    }
}