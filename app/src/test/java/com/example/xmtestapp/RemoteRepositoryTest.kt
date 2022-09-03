package com.example.xmtestapp

import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.ApiInterface
import com.example.xmtestapp.data.db.repository.QuestionRepositoryRemoteImpl
import com.example.xmtestapp.data.interfaces.QuestionRepositoryRemote
import com.example.xmtestapp.domain.entity.Answer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RemoteRepositoryTest {

    @Mock
    lateinit var mockWebServer: MockWebServer

    @Mock
    lateinit var apiInterface: ApiInterface
    lateinit var gson: Gson

    private lateinit var apiClient: ApiClient
    private lateinit var questionRepositoryRemote: QuestionRepositoryRemote

    @Before
    fun setup() {
        gson = GsonBuilder().create()
        mockWebServer = MockWebServer()

        apiInterface = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiInterface::class.java)


        apiClient = ApiClient(apiInterface)
        questionRepositoryRemote = QuestionRepositoryRemoteImpl(apiClient)
    }

    @After
    fun deconstruct() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetQuestionsEndpointError() = runTest {
        val mockResponse = MockResponse()

        mockWebServer.enqueue(
            mockResponse.setBody("[]")
        )

        val questionsList = questionRepositoryRemote.getQuestions()

        assert(questionsList.isEmpty())
    }

    @Test
    fun testGetQuestionsEndpointSuccess() = runTest {
        val mockResponse = MockResponse()

        val mockResp = """[{"id":"1", "question":"?"}]"""

        mockWebServer.enqueue(
            mockResponse.setBody(mockResp)
        )

        val questionsList = questionRepositoryRemote.getQuestions()

        assert(questionsList.isNotEmpty())
    }

    @Test
    fun testSubmitAnswerEndpoint() = runTest {
        val mockResponse = MockResponse()

        mockWebServer.enqueue(
            mockResponse.setBody("true")
        )

        val isSuccess = questionRepositoryRemote.submitAnswer(Answer(1, "asd"))

        assert(isSuccess)
    }
}