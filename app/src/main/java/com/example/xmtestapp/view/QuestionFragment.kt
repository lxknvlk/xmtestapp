package com.example.xmtestapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.example.xmtestapp.R
import com.example.xmtestapp.data.api.entity.QuestionEntity

private const val ARG_ID = "ARG_ID"
private const val ARG_QUESTION = "ARG_QUESTION"
private const val ARG_ANSWER = "ARG_ANSWER"


class QuestionFragment : Fragment() {
    private lateinit var btnSubmit: Button
    private lateinit var etAnswer: EditText
    private lateinit var tvQuestion: TextView


    private var qId: Int = 0
    private var question: String? = null
    private var answer: String? = null

    fun setNewData(questionEntity: QuestionEntity){
        if (questionEntity.answer != null) {
            answer = questionEntity.answer
            btnSubmit.isEnabled = false
            etAnswer.setText(answer)
            etAnswer.isEnabled = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            qId = it.getInt(ARG_ID)
            question = it.getString(ARG_QUESTION)
            answer = it.getString(ARG_ANSWER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        tvQuestion = view.findViewById<TextView>(R.id.tvQuestion)
        etAnswer = view.findViewById<EditText>(R.id.etAnswer)
        btnSubmit = view.findViewById<Button>(R.id.btnSubmit)

        tvQuestion.text = question

        if (answer != null) {
            btnSubmit.isEnabled = false
            etAnswer.setText(answer)
            etAnswer.isEnabled = false
        }

        btnSubmit.setOnClickListener {
            val text = etAnswer.text.toString().trim()
            (activity as SurveyActivity).submitAnswer(id = qId, answer = text)
        }

        etAnswer.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()

                if (answer == null) btnSubmit.isEnabled = text.isNotEmpty()
            }
        })

        return view
    }

    companion object {
        fun newInstance(questionEntity: QuestionEntity): QuestionFragment{
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_ID, questionEntity.id)
            args.putString(ARG_QUESTION, questionEntity.question)
            args.putString(ARG_ANSWER, questionEntity.answer)
            fragment.arguments = args

            return fragment
        }
    }
}