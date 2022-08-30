package com.example.xmtestapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.xmtestapp.R
import com.example.xmtestapp.data.api.entity.QuestionEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {

    private var barMenu: Menu? = null
    private lateinit var vpPager: ViewPager2
    private lateinit var questionPagerAdapter: QuestionPagerAdapter

    private val viewModel: SurveyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vpPager = findViewById(R.id.vpPager)

        viewModel.questionsLiveData.observe(this) { questionsList ->
            questionPagerAdapter = QuestionPagerAdapter(this, questionsList)
            vpPager.adapter = questionPagerAdapter
            setQuestionNumber(1)
        }

        vpPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setQuestionNumber(position + 1)
                toggleMenuButtons(position)
                super.onPageSelected(position)
            }
        })

        viewModel.getQuestionsFromRepo()
    }

    private fun toggleMenuButtons(position: Int) {
        barMenu?.findItem(R.id.action_previous)?.isEnabled = true
        barMenu?.findItem(R.id.action_next)?.isEnabled = true

        when (position) {
            0 -> barMenu?.findItem(R.id.action_previous)?.isEnabled = false
            viewModel.totalQuestions - 1 -> barMenu?.findItem(R.id.action_next)?.isEnabled = false
        }
    }

    fun setQuestionNumber(currentPage: Int){
        val text = "${getString(R.string.question)} $currentPage/${viewModel.totalQuestions}"
        supportActionBar?.title = text
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.survey_bar_menu, menu)
        barMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_previous -> {
                vpPager.previousPage(true)
                true
            }

            R.id.action_next -> {
                vpPager.nextPage(true)
                true
            }

            else -> false
        }
    }
}