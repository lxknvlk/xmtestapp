package com.example.xmtestapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
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