package com.example.xmtestapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.xmtestapp.data.db.dao.AnswerDAO
import com.example.xmtestapp.data.db.dao.QuestionDAO
import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.api.entity.QuestionEntity

@Database(entities = [QuestionEntity::class, AnswerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDAO
    abstract fun answerDao(): AnswerDAO
}