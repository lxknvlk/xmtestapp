package com.example.xmtestapp.data.db.repository

import com.example.xmtestapp.data.db.dao.AnswerDAO
import com.example.xmtestapp.data.api.entity.AnswerEntity
import javax.inject.Inject

class AnswerRepository @Inject constructor(
    private val answerDAO: AnswerDAO
) {
    fun getAll(): List<AnswerEntity> {
        return answerDAO.getAll()
    }

    fun insert(answerEntity: AnswerEntity) {
        answerDAO.insert(answerEntity)
    }
}