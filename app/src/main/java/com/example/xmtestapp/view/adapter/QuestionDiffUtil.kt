package com.example.xmtestapp.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.xmtestapp.data.api.entity.QuestionEntity

class QuestionDiffUtil(
    private val oldList: List<QuestionEntity>,
    private val newList: List<QuestionEntity>
) : DiffUtil.Callback() {
    enum class PayloadKey {
        EVERYTHING
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.question == newItem.question && oldItem.answer == newItem.answer
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return listOf(PayloadKey.EVERYTHING)
    }
}