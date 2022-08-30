package com.example.xmtestapp.data.db.repository

import com.example.xmtestapp.data.db.dao.QuestionDAO
import com.example.xmtestapp.data.api.entity.QuestionEntity
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val questionDAO: QuestionDAO
) {
    fun getAll(): List<QuestionEntity> {
        return questionDAO.getAll()
    }

    fun insertAll(questionsList: List<QuestionEntity>) {
        questionDAO.insertAll(questionsList)
    }

    fun updateAnswer(id: Int, answer: String){
        questionDAO.update(id, answer)
    }
}
