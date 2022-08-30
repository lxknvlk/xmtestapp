package com.example.xmtestapp.view

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.xmtestapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener {

        }

        viewModel.fetchQuestions()
    }
}