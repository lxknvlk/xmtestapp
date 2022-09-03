package com.example.xmtestapp.data.db.repository

import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.data.db.dao.QuestionDAO
import com.example.xmtestapp.data.interfaces.QuestionRepositoryLocal
import com.example.xmtestapp.domain.entity.Question
import javax.inject.Inject

class QuestionRepositoryLocalImpl @Inject constructor(
    private val questionDAO: QuestionDAO
) : QuestionRepositoryLocal {
    override fun getAll(): List<Question> {
        return questionDAO.getAll().map { it.toQuestion() }
    }

    override fun insertAll(questionsList: List<Question>) {
        questionDAO.insertAll(questionsList.map { QuestionEntity.fromQuestion(it) })
    }

    override fun updateAnswer(id: Int, answer: String) {
        questionDAO.update(id, answer)
    }
}
