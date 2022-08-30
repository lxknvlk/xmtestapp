package com.example.xmtestapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.xmtestapp.R
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var llRoot: LinearLayout
    private lateinit var piLoader: LinearProgressIndicator

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)
        llRoot = findViewById(R.id.llRoot)
        piLoader = findViewById(R.id.piLoader)

        btnStart.setOnClickListener {
            val intent = Intent(this, SurveyActivity::class.java)
            startActivity(intent)
        }

        viewModel.loadingLiveData.observe(this) { isLoading ->
            piLoader.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.questionsLiveData.observe(this) { questionsList ->
            if (questionsList != null) {
                btnStart.isEnabled = true
                showSuccess(getString(R.string.questions_loaded))
            } else {
                showError(getString(R.string.server_error))
            }
        }

        viewModel.fetchQuestions()
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