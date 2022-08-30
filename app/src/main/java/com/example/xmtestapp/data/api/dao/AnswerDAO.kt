package com.example.xmtestapp.data.api.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.xmtestapp.data.api.entity.AnswerEntity

@Dao
interface AnswerDAO {
    @Query("SELECT * FROM answer")
    fun getAll(): List<AnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(answer: AnswerEntity)
}