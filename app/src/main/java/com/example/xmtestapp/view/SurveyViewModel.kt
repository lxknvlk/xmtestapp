package com.example.xmtestapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtestapp.data.api.entity.AnswerEntity
import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.logic.GetQuestionsUseCase
import com.example.xmtestapp.logic.SubmitAnswerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SubmitAnswerState {
    STATE_DEFAULT,
    STATE_SUCCESS,
    STATE_ERROR
}

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase
) : ViewModel() {

    val questionsLiveData = MutableLiveData<List<QuestionEntity>>()
    val answeredQuestionsLiveData = MutableLiveData<List<AnswerEntity>>()
    val submitAnswerStateLiveData = MutableLiveData<SubmitAnswerState>(SubmitAnswerState.STATE_DEFAULT)

    var totalQuestions: Int = 0

    fun getQuestionsFromRepo() {
        viewModelScope.launch(Dispatchers.IO) {
            val questions = getQuestionsUseCase.getQuestionsFromRepo()
            totalQuestions = questions.size
            questionsLiveData.postValue(questions)
        }
    }

    fun submitAnswer(id: Int, answer: String){
        viewModelScope.launch(Dispatchers.IO) {
            val success = submitAnswerUseCase.submitAnswer(id, answer)

            if (success) {
                refreshAnsweredQuestions()
                submitAnswerStateLiveData.postValue(SubmitAnswerState.STATE_SUCCESS)
            } else {
                submitAnswerStateLiveData.postValue(SubmitAnswerState.STATE_ERROR)
            }
        }
    }

    private fun refreshAnsweredQuestions(){
        viewModelScope.launch(Dispatchers.IO) {
            val answeredQuestions = submitAnswerUseCase.getAnsweredQuestions()
            answeredQuestionsLiveData.postValue(answeredQuestions)
        }
    }
}
