package com.example.xmtestapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.domain.SurveyUseCase
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
    private val surveyUseCase: SurveyUseCase,
) : ViewModel() {

    val questionsLiveData = MutableLiveData<List<QuestionEntity>>()
    val loadingLiveData = MutableLiveData<Boolean>(false)
    val submitAnswerStateLiveData = MutableLiveData<SubmitAnswerState>(SubmitAnswerState.STATE_DEFAULT)

    var totalQuestions: Int = 0

    fun refreshQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            val questions = surveyUseCase.getQuestionsFromRepo()
            totalQuestions = questions.size
            questionsLiveData.postValue(questions)
        }
    }

    fun submitAnswer(id: Int, answer: String){
        loadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val success = surveyUseCase.submitAnswer(id, answer)

            if (success) {
                refreshQuestions()
                submitAnswerStateLiveData.postValue(SubmitAnswerState.STATE_SUCCESS)
            } else {
                submitAnswerStateLiveData.postValue(SubmitAnswerState.STATE_ERROR)
            }

            loadingLiveData.postValue(false)
        }
    }
}
