package com.example.xmtestapp.data.interfaces

import com.example.xmtestapp.domain.entity.Question

interface QuestionRepositoryLocal {
    fun getAll(): List<Question>
    fun insertAll(questionsList: List<Question>)
    fun updateAnswer(id: Int, answer: String)
}