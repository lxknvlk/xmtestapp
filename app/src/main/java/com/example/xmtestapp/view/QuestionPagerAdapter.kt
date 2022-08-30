package com.example.xmtestapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.xmtestapp.data.api.entity.QuestionEntity

class QuestionPagerAdapter(
    fragmentActivity: FragmentActivity,
    var questionList: List<QuestionEntity>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun createFragment(position: Int): Fragment {
        val questionEntity = questionList[position]
        return QuestionFragment.newInstance(questionEntity)
    }
}