package com.turtle.amatda.presentation.di.module

import android.util.Log
import com.google.gson.GsonBuilder
import com.turtle.amatda.data.api.ApiClient
import com.turtle.amatda.data.api.WeatherAPIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(ApiClient.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherAPIService(retrofit: Retrofit): WeatherAPIService {
        return retrofit.create(WeatherAPIService::class.java)
    }

    // okhttp 인스턴스 생성
    @Singleton
    @Provides
    fun provideOkHttpClient(htpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(htpLoggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)  // 커넥션 타임아웃
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    // 로그를 찍기 위한 로깅 인터셉터 설정
    @Provides
    fun provideHttpLogginInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("amatda_api_call : ", message.toString())
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}