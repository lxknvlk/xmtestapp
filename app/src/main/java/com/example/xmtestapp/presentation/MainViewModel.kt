package com.example.xmtestapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmtestapp.domain.interfaces.DownloadQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val downloadQuestionsUseCase: DownloadQuestionsUseCase,
    private val coroutineDispatcherIO: CoroutineDispatcher
) : ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>(false)
    val questionsLiveData = MutableLiveData<Boolean>()

    fun downloadQuestions() {
        loadingLiveData.postValue(true)
        viewModelScope.launch(coroutineDispatcherIO) {
            val isSuccess = downloadQuestionsUseCase.downloadQuestions()
            loadingLiveData.postValue(false)
            questionsLiveData.postValue(isSuccess)
        }
    }
}
