package com.example.xmtestapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtestapp.logic.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    val successLiveData = MutableLiveData<Any?>(false)

    fun fetchQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            val questions = getQuestionsUseCase.getQuestions()
            successLiveData.postValue(questions)
        }
    }
}
