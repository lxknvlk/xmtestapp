package com.example.xmtestapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xmtestapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
    }
}