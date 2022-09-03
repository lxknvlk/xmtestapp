package com.example.xmtestapp.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.example.xmtestapp.domain.entity.Question
import com.example.xmtestapp.presentation.QuestionFragment

class QuestionPagerAdapter(
    private val fragmentActivity: FragmentActivity,
    private val questionList: MutableList<Question>
) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val tag = "f" + holder.itemId
            val fragment = fragmentActivity.supportFragmentManager.findFragmentByTag(tag)
            if (fragment != null) {
                (fragment as QuestionFragment).setNewData(questionList[position])
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun createFragment(position: Int): Fragment {
        val questionEntity = questionList[position]
        return QuestionFragment.newInstance(questionEntity)
    }

    fun updateQuestions(updatedList: List<Question>) {
        val diffResult = DiffUtil.calculateDiff(QuestionDiffUtil(questionList, updatedList))
        questionList.clear()
        questionList.addAll(updatedList)
        diffResult.dispatchUpdatesTo(this)
    }
}

fun ViewPager2.nextPage(smoothScroll: Boolean = true): Boolean {
    if ((currentItem + 1) < (adapter?.itemCount ?: 0)) {
        setCurrentItem(currentItem + 1, smoothScroll)
        return true
    }
    return false
}

fun ViewPager2.previousPage(smoothScroll: Boolean = true): Boolean {
    if ((currentItem - 1) >= 0) {
        setCurrentItem(currentItem - 1, smoothScroll)
        return true
    }
    return false
}