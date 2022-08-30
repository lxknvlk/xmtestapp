package com.example.xmtestapp.data.api.repository

import com.example.xmtestapp.data.api.dao.QuestionDAO
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
}
