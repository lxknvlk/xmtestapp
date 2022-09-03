package com.example.xmtestapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.ApiInterface
import com.example.xmtestapp.data.api.RetrofitClient
import com.example.xmtestapp.data.db.AppDatabase
import com.example.xmtestapp.data.db.dao.QuestionDAO
import com.example.xmtestapp.data.db.repository.QuestionRepositoryLocalImpl
import com.example.xmtestapp.data.db.repository.QuestionRepositoryRemoteImpl
import com.example.xmtestapp.data.interfaces.QuestionRepositoryLocal
import com.example.xmtestapp.data.interfaces.QuestionRepositoryRemote
import com.example.xmtestapp.domain.interfaces.DownloadQuestionsUseCase
import com.example.xmtestapp.domain.interfaces.GetQuestionsUseCase
import com.example.xmtestapp.domain.interfaces.SubmitAnswerUseCase
import com.example.xmtestapp.domain.usecase.DownloadQuestionsUseCaseImpl
import com.example.xmtestapp.domain.usecase.GetQuestionsUseCaseImpl
import com.example.xmtestapp.domain.usecase.SubmitAnswerUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

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
    fun provideQuestionDao(appDatabase: AppDatabase): QuestionDAO {
        return appDatabase.questionDao()
    }

    @Provides
    fun provideQuestionRepositoryLocal(questionDAO: QuestionDAO): QuestionRepositoryLocal {
        return QuestionRepositoryLocalImpl(questionDAO)
    }

    @Provides
    fun provideQuestionRepositoryRemote(apiClient: ApiClient): QuestionRepositoryRemote {
        return QuestionRepositoryRemoteImpl(apiClient)
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
        questionRepositoryLocal: QuestionRepositoryLocalImpl
    ): GetQuestionsUseCase {
        return GetQuestionsUseCaseImpl(questionRepositoryLocal)
    }

    @Provides
    fun provideSubmitAnswerUseCase(
        questionRepositoryLocal: QuestionRepositoryLocalImpl,
        questionRepositoryRemote: QuestionRepositoryRemoteImpl
    ): SubmitAnswerUseCase {
        return SubmitAnswerUseCaseImpl(questionRepositoryLocal, questionRepositoryRemote)
    }

    @Provides
    fun provideDownloadQuestionsUseCase(
        questionRepositoryLocal: QuestionRepositoryLocalImpl,
        questionRepositoryRemote: QuestionRepositoryRemoteImpl
    ): DownloadQuestionsUseCase {
        return DownloadQuestionsUseCaseImpl(questionRepositoryLocal, questionRepositoryRemote)
    }

    @Provides
    fun provideCoroutineDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}