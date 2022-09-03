package com.example.xmtestapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtestapp.domain.entity.Question
import com.example.xmtestapp.domain.interfaces.GetQuestionsUseCase
import com.example.xmtestapp.domain.interfaces.SubmitAnswerUseCase
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

    val questionsLiveData = MutableLiveData<List<Question>>()
    val loadingLiveData = MutableLiveData<Boolean>(false)
    val submitAnswerStateLiveData = MutableLiveData(SubmitAnswerState.STATE_DEFAULT)

    var totalQuestions: Int = 0

    fun populateQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            val questions = getQuestionsUseCase.getQuestionsFromRepo()
            totalQuestions = questions.size
            questionsLiveData.postValue(questions)
        }
    }

    fun submitAnswer(id: Int, answer: String) {
        loadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val success = submitAnswerUseCase.submitAnswer(id, answer)

            if (success) {
                populateQuestions()
                submitAnswerStateLiveData.postValue(SubmitAnswerState.STATE_SUCCESS)
            } else {
                submitAnswerStateLiveData.postValue(SubmitAnswerState.STATE_ERROR)
            }

            loadingLiveData.postValue(false)
        }
    }
}
