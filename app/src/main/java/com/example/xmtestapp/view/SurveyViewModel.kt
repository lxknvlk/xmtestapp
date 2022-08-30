package com.example.xmtestapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtestapp.data.api.entity.QuestionEntity
import com.example.xmtestapp.logic.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    val questionsLiveData = MutableLiveData<List<QuestionEntity>>()

    fun getQuestionsFromRepo() {
        viewModelScope.launch(Dispatchers.IO) {
            val questions = getQuestionsUseCase.getQuestionsFromRepo()
            questionsLiveData.postValue(questions)
        }
    }
}
