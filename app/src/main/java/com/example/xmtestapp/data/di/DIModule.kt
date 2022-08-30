package com.example.xmtestapp.data.di

import com.example.xmtestapp.data.api.ApiClient
import com.example.xmtestapp.data.api.ApiInterface
import com.example.xmtestapp.data.api.RetrofitClient
import com.example.xmtestapp.logic.GetQuestionsUseCase
import com.example.xmtestapp.logic.SubmitAnswerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideGetQuestionsUseCase(
        apiClient: ApiClient
    ): GetQuestionsUseCase {
        return GetQuestionsUseCase(apiClient)
    }

    @Provides
    fun provideSubmitAnswerUseCase(
        apiClient: ApiClient
    ): SubmitAnswerUseCase {
        return SubmitAnswerUseCase(apiClient)
    }
}