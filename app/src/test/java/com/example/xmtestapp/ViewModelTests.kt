package com.example.xmtestapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.xmtestapp.domain.entity.Question
import com.example.xmtestapp.domain.interfaces.DownloadQuestionsUseCase
import com.example.xmtestapp.domain.interfaces.GetQuestionsUseCase
import com.example.xmtestapp.domain.interfaces.SubmitAnswerUseCase
import com.example.xmtestapp.presentation.MainViewModel
import com.example.xmtestapp.presentation.SubmitAnswerState
import com.example.xmtestapp.presentation.SurveyViewModel
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class ViewModelTests {

    @Mock
    private lateinit var downloadQuestionsUseCase: DownloadQuestionsUseCase

    @Mock
    private lateinit var getQuestionsUseCase: GetQuestionsUseCase

    @Mock
    private lateinit var submitAnswerUseCase: SubmitAnswerUseCase

    private lateinit var mainViewModel: MainViewModel
    private lateinit var surveyViewModel: SurveyViewModel

    private val dispatcher = StandardTestDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)

        mainViewModel = MainViewModel(downloadQuestionsUseCase, dispatcher)
        surveyViewModel = SurveyViewModel(getQuestionsUseCase, submitAnswerUseCase, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testMainViewModelQuestionsLiveData() = runTest(UnconfinedTestDispatcher()) {
        Mockito.`when`(downloadQuestionsUseCase.downloadQuestions()).thenReturn(true)

        mainViewModel.downloadQuestions()
        advanceUntilIdle()

        assertEquals(mainViewModel.questionsLiveData.value, true)
    }

    @Test
    fun testSurveyViewModelQuestionsLiveData() = runTest(UnconfinedTestDispatcher()) {
        val questionList = listOf(Question(1, "que", null))
        Mockito.`when`(getQuestionsUseCase.getQuestionsFromRepo()).thenReturn(questionList)

        surveyViewModel.populateQuestions()
        advanceUntilIdle()

        assertEquals(surveyViewModel.questionsLiveData.value, questionList)
    }

    @Test
    fun testSurveyViewModelSubmitAnswerLiveData() = runTest(UnconfinedTestDispatcher()) {
        Mockito.`when`(submitAnswerUseCase.submitAnswer(1, "ans")).thenReturn(true)

        surveyViewModel.submitAnswer(1, "ans")
        advanceUntilIdle()

        assertEquals(surveyViewModel.submitAnswerStateLiveData.value, SubmitAnswerState.STATE_SUCCESS)
    }
}