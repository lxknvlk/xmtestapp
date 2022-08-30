package com.example.xmtestapp.data.api.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.xmtestapp.data.api.entity.QuestionEntity

@Dao
interface QuestionDAO {
    @Query("SELECT * FROM question")
    fun getAll(): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questions: List<QuestionEntity>)
}