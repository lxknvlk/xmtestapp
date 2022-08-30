package com.example.xmtestapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtestapp.data.api.ApiClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val successLiveData = MutableLiveData<Any?>(false)

    private val apiClient = ApiClient()

    fun fetchQuestions() {
        viewModelScope.launch {
            val questions = apiClient.getQuestions()
            successLiveData.postValue(questions)
        }
    }
}
