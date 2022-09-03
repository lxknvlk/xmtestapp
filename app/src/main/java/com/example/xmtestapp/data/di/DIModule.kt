package com.example.xmtestapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.ApiInterface
import com.example.xmtestapp.data.api.RetrofitClient
import com.example.xmtestapp.data.db.AppDatabase
import com.example.xmtestapp.data.db.repository.QuestionRepositoryLocal
import com.example.xmtestapp.data.db.repository.QuestionRepositoryRemote
import com.example.xmtestapp.domain.usecase.DownloadQuestionsUseCase
import com.example.xmtestapp.domain.usecase.GetQuestionsUseCase
import com.example.xmtestapp.domain.usecase.SubmitAnswerUseCase
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
    fun provideQuestionRepositoryLocal(appDatabase: AppDatabase): QuestionRepositoryLocal {
        return QuestionRepositoryLocal(appDatabase.questionDao())
    }

    @Provides
    fun provideQuestionRepositoryRemote(apiClient: ApiClient): QuestionRepositoryRemote {
        return QuestionRepositoryRemote(apiClient)
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
        questionRepositoryLocal: QuestionRepositoryLocal
    ): GetQuestionsUseCase {
        return GetQuestionsUseCase(questionRepositoryLocal)
    }

    @Provides
    fun provideSubmitAnswerUseCase(
        questionRepositoryLocal: QuestionRepositoryLocal,
        questionRepositoryRemote: QuestionRepositoryRemote
    ): SubmitAnswerUseCase {
        return SubmitAnswerUseCase(questionRepositoryLocal, questionRepositoryRemote)
    }

    @Provides
    fun provideDownloadQuestionsUseCase(
        questionRepositoryLocal: QuestionRepositoryLocal,
        questionRepositoryRemote: QuestionRepositoryRemote
    ): DownloadQuestionsUseCase {
        return DownloadQuestionsUseCase(questionRepositoryLocal, questionRepositoryRemote)
    }
}