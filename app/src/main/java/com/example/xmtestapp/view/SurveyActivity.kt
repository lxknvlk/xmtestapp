package com.example.xmtestapp.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.xmtestapp.R
import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.view.adapter.QuestionPagerAdapter
import com.example.xmtestapp.view.adapter.nextPage
import com.example.xmtestapp.view.adapter.previousPage
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {

    private var barMenu: Menu? = null
    private var questionPagerAdapter: QuestionPagerAdapter? = null

    private lateinit var vpPager: ViewPager2
    private lateinit var llRoot: LinearLayout
    private lateinit var tvQuestionsSubmitted: TextView
    private lateinit var piLoader: LinearProgressIndicator

    private val viewModel: SurveyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vpPager = findViewById(R.id.vpPager)
        tvQuestionsSubmitted = findViewById(R.id.tvQuestionsSubmitted)
        llRoot = findViewById(R.id.llRoot)
        piLoader = findViewById(R.id.piLoader)

        vpPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setQuestionNumber(position + 1)
                toggleMenuButtons(position)
                super.onPageSelected(position)
            }
        })

        initObservers()

        viewModel.refreshQuestions()
    }

    private fun initObservers() {
        viewModel.questionsLiveData.observe(this) { questionsList ->
            questionsList?.let { refreshQuestionsList(questionsList) }
        }

        viewModel.submitAnswerStateLiveData.observe(this) { submitAnswerState ->
            when (submitAnswerState) {
                SubmitAnswerState.STATE_SUCCESS -> showSuccess(getString(R.string.answer_submitted))
                SubmitAnswerState.STATE_ERROR -> showError(getString(R.string.answer_submit_error))
            }
        }

        viewModel.loadingLiveData.observe(this) { isLoading ->
            piLoader.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun refreshQuestionsList(questionsList: List<QuestionEntity>) {
        if (questionPagerAdapter == null){
            questionPagerAdapter = QuestionPagerAdapter(this, questionsList.toMutableList())
            vpPager.adapter = questionPagerAdapter
            setQuestionNumber(1)
        } else {
            questionPagerAdapter?.updateQuestions(questionsList)
        }

        setAnsweredQuestionsNumber(questionsList)
    }

    private fun setAnsweredQuestionsNumber(questionsList: List<QuestionEntity>) {
        val answeredQuestions = questionsList.filter { it.answer != null }
        tvQuestionsSubmitted.text =
            "${getString(R.string.questions_submitted)} ${answeredQuestions.size}"

    }

    private fun toggleMenuButtons(position: Int) {
        barMenu?.findItem(R.id.action_previous)?.isEnabled = true
        barMenu?.findItem(R.id.action_next)?.isEnabled = true

        when (position) {
            0 -> barMenu?.findItem(R.id.action_previous)?.isEnabled = false
            viewModel.totalQuestions - 1 -> barMenu?.findItem(R.id.action_next)?.isEnabled = false
        }
    }

    fun submitAnswer(id: Int, answer: String) {
        viewModel.submitAnswer(id, answer)
    }

    fun setQuestionNumber(currentPage: Int) {
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

    private fun showError(msg: String) {
        Snackbar.make(llRoot, msg, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error_red)).show()
    }

    private fun showSuccess(msg: String) {
        Snackbar.make(llRoot, msg, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.success_green)).show()
    }
}