package com.example.xmtestapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtestapp.data.api.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiClient: ApiClient
) : ViewModel() {

    val successLiveData = MutableLiveData<Any?>(false)

    fun fetchQuestions() {
        viewModelScope.launch {
            val questions = apiClient.getQuestions()
            successLiveData.postValue(questions)
        }
    }
}
