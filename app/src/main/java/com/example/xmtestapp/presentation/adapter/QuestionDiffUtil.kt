package com.example.xmtestapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.xmtestapp.domain.entity.Question

class QuestionDiffUtil(
    private val oldList: List<Question>,
    private val newList: List<Question>
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

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        return listOf(PayloadKey.EVERYTHING)
    }
}