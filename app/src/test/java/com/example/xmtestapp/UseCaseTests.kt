package com.example.xmtestapp

import com.example.xmtestapp.data.interfaces.QuestionRepositoryLocal
import com.example.xmtestapp.data.interfaces.QuestionRepositoryRemote
import com.example.xmtestapp.domain.entity.Answer
import com.example.xmtestapp.domain.entity.Question
import com.example.xmtestapp.domain.interfaces.DownloadQuestionsUseCase
import com.example.xmtestapp.domain.interfaces.GetQuestionsUseCase
import com.example.xmtestapp.domain.interfaces.SubmitAnswerUseCase
import com.example.xmtestapp.domain.usecase.DownloadQuestionsUseCaseImpl
import com.example.xmtestapp.domain.usecase.GetQuestionsUseCaseImpl
import com.example.xmtestapp.domain.usecase.SubmitAnswerUseCaseImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class UseCaseTests {

    private lateinit var downloadQuestionsUseCase: DownloadQuestionsUseCase
    private lateinit var getQuestionsUseCase: GetQuestionsUseCase
    private lateinit var submitAnswerUseCase: SubmitAnswerUseCase

    @Mock
    private lateinit var questionRepositoryRemote: QuestionRepositoryRemote

    @Mock
    private lateinit var questionRepositoryLocal: QuestionRepositoryLocal

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        downloadQuestionsUseCase =
            DownloadQuestionsUseCaseImpl(questionRepositoryLocal, questionRepositoryRemote)

        getQuestionsUseCase = GetQuestionsUseCaseImpl(questionRepositoryLocal)

        submitAnswerUseCase =
            SubmitAnswerUseCaseImpl(questionRepositoryLocal, questionRepositoryRemote)
    }

    @Test
    fun testDownloadQuestionsUseCase() = runTest {
        val questionList = listOf(Question(1, "que", null))
        Mockito.`when`(questionRepositoryRemote.getQuestions()).thenReturn(questionList)
        assertEquals(downloadQuestionsUseCase.downloadQuestions(), true)
    }

    @Test
    fun testGetQuestionsUseCase() = runTest {
        val questionList = listOf(Question(1, "que", null))
        Mockito.`when`(questionRepositoryLocal.getAll()).thenReturn(questionList)
        assertEquals(getQuestionsUseCase.getQuestionsFromRepo(), questionList)
    }

    @Test
    fun testSubmitAnswerUseCase() = runTest {
        val answer = Answer(1, "ans")
        Mockito.`when`(questionRepositoryRemote.submitAnswer(answer)).thenReturn(true)
        assertEquals(submitAnswerUseCase.submitAnswer(answer.id, answer.answer), true)
    }
}