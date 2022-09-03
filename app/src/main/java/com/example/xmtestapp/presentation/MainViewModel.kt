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

@HiltViewModel
class MainViewModel @Inject constructor(
    private val surveyUseCase: SurveyUseCase
) : ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>(false)
    val questionsLiveData = MutableLiveData<List<QuestionEntity>?>()

    fun fetchQuestions() {
        loadingLiveData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val questions = surveyUseCase.getQuestionsFromBackend()
            loadingLiveData.postValue(false)
            questionsLiveData.postValue(questions)
        }
    }
}
