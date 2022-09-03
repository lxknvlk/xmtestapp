package com.example.xmtestapp.domain.interfaces

import com.example.xmtestapp.domain.entity.Question

interface GetQuestionsUseCase {
    suspend fun getQuestionsFromRepo(): List<Question>
}