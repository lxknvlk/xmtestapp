package com.example.xmtestapp.data.db.repository

import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.data.db.dao.QuestionDAO
import com.example.xmtestapp.domain.entity.Question
import javax.inject.Inject

class QuestionRepositoryLocal @Inject constructor(
    private val questionDAO: QuestionDAO
) {
    fun getAll(): List<Question> {
        return questionDAO.getAll().map { it.toQuestion() }
    }

    fun insertAll(questionsList: List<Question>) {
        questionDAO.insertAll(questionsList.map { QuestionEntity.fromQuestion(it) })
    }

    fun updateAnswer(id: Int, answer: String) {
        questionDAO.update(id, answer)
    }
}
