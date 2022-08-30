package com.example.xmtestapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.xmtestapp.R
import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {

    private var barMenu: Menu? = null
    private lateinit var vpPager: ViewPager2
    private lateinit var questionPagerAdapter: QuestionPagerAdapter
    private lateinit var llRoot: LinearLayout
    private lateinit var tvQuestionsSubmitted: TextView

    private val viewModel: SurveyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vpPager = findViewById(R.id.vpPager)
        tvQuestionsSubmitted = findViewById(R.id.tvQuestionsSubmitted)
        llRoot = findViewById(R.id.llRoot)

        vpPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setQuestionNumber(position + 1)
                toggleMenuButtons(position)
                super.onPageSelected(position)
            }
        })

        initObservers()

        viewModel.getQuestionsFromRepo()
    }

    private fun initObservers() {
        viewModel.questionsLiveData.observe(this) { questionsList ->
            questionPagerAdapter = QuestionPagerAdapter(this, questionsList)
            vpPager.adapter = questionPagerAdapter
            setQuestionNumber(1)
        }

        viewModel.answeredQuestionsLiveData.observe(this) { answeredQuestions ->
            tvQuestionsSubmitted.text = "${getString(R.string.questions_submitted)} ${answeredQuestions.size}"
        }

        viewModel.submitAnswerStateLiveData.observe(this) { submitAnswerState ->
            when (submitAnswerState) {
                SubmitAnswerState.STATE_SUCCESS -> showSuccess(getString(R.string.answer_submitted))
                SubmitAnswerState.STATE_ERROR -> showError(getString(R.string.answer_submit_error))
            }
        }
    }

    private fun toggleMenuButtons(position: Int) {
        barMenu?.findItem(R.id.action_previous)?.isEnabled = true
        barMenu?.findItem(R.id.action_next)?.isEnabled = true

        when (position) {
            0 -> barMenu?.findItem(R.id.action_previous)?.isEnabled = false
            viewModel.totalQuestions - 1 -> barMenu?.findItem(R.id.action_next)?.isEnabled = false
        }
    }

    fun submitAnswer(id: Int, answer: String){
        viewModel.submitAnswer(id, answer)
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

    private fun showError(msg: String) {
        Snackbar.make(llRoot, msg, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error_red)).show()
    }

    private fun showSuccess(msg: String) {
        Snackbar.make(llRoot, msg, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.success_green)).show()
    }
}