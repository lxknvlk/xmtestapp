package com.example.xmtestapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.ApiInterface
import com.example.xmtestapp.data.api.RetrofitClient
import com.example.xmtestapp.data.api.db.AppDatabase
import com.example.xmtestapp.data.api.repository.AnswerRepository
import com.example.xmtestapp.data.api.repository.QuestionRepository
import com.example.xmtestapp.logic.GetQuestionsUseCase
import com.example.xmtestapp.logic.SubmitAnswerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DiModule {

    @Provides
    fun provideApiInterface(retrofitClient: RetrofitClient): ApiInterface {
        return retrofitClient.getInstance().create(ApiInterface::class.java)
    }

    @Provides
    fun provideApiClient(apiInterface: ApiInterface): ApiClient {
        return ApiClient(apiInterface)
    }

    @Provides
    fun provideRetrofitClient(): RetrofitClient {
        return RetrofitClient()
    }

    @Provides
    fun provideQuestionRepository(appDatabase: AppDatabase): QuestionRepository {
        return QuestionRepository(appDatabase.questionDao())
    }

    @Provides
    fun provideAnswerRepository(appDatabase: AppDatabase): AnswerRepository {
        return AnswerRepository(appDatabase.answerDao())
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "database"
        ).build()
    }

    @Provides
    fun provideGetQuestionsUseCase(
        apiClient: ApiClient,
        questionRepository: QuestionRepository
    ): GetQuestionsUseCase {
        return GetQuestionsUseCase(apiClient, questionRepository)
    }

    @Provides
    fun provideSubmitAnswerUseCase(
        apiClient: ApiClient
    ): SubmitAnswerUseCase {
        return SubmitAnswerUseCase(apiClient)
    }
}