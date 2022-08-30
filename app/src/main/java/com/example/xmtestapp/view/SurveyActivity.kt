package com.example.xmtestapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.xmtestapp.R
import com.example.xmtestapp.data.api.entity.QuestionEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {

    private lateinit var vpPager: ViewPager2
    private lateinit var questionPagerAdapter: QuestionPagerAdapter

    private val viewModel: SurveyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        vpPager = findViewById(R.id.vpPager)

        viewModel.questionsLiveData.observe(this) { questionsList ->
            questionPagerAdapter = QuestionPagerAdapter(this, questionsList)
            vpPager.adapter = questionPagerAdapter
        }

        viewModel.getQuestionsFromRepo()
    }
}