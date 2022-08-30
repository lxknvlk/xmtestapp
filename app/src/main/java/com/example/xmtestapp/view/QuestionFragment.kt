package com.example.xmtestapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.xmtestapp.R
import com.example.xmtestapp.data.api.entity.QuestionEntity

private const val ARG_ID = "ARG_ID"
private const val ARG_QUESTION = "ARG_QUESTION"


class QuestionFragment : Fragment() {
    private var qId: Int = 0
    private var question: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            qId = it.getInt(ARG_ID)
            question = it.getString(ARG_QUESTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val tvQuestion = view.findViewById<TextView>(R.id.tvQuestion)
        val etAnswer = view.findViewById<EditText>(R.id.etAnswer)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        tvQuestion.text = question

        return view
    }

    companion object {
        fun newInstance(questionEntity: QuestionEntity): QuestionFragment{
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_ID, questionEntity.id)
            args.putString(ARG_QUESTION, questionEntity.question)
            fragment.arguments = args

            return fragment
        }
    }
}