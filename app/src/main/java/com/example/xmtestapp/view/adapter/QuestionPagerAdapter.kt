package com.example.xmtestapp.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.view.QuestionFragment

class QuestionPagerAdapter(
    val fragmentActivity: FragmentActivity,
    val questionList: MutableList<QuestionEntity>
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

    override fun getItemId(position: Int): Long {
        return questionList[position].id.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return questionList.any { it.id.toLong() == itemId }
    }

    override fun createFragment(position: Int): Fragment {
        val questionEntity = questionList[position]
        return QuestionFragment.newInstance(questionEntity)
    }

    fun updateQuestions(updatedList: List<QuestionEntity>) {
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
    //can't move to next page, maybe current page is last or adapter not set.
    return false
}

fun ViewPager2.previousPage(smoothScroll: Boolean = true): Boolean {
    if ((currentItem - 1) >= 0) {
        setCurrentItem(currentItem - 1, smoothScroll)
        return true
    }
    //can't move to previous page, maybe current page is first or adapter not set.
    return false
}