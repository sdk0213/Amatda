package com.turtle.amatda.presentation.android.di.module

import android.util.Log
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import com.turtle.amatda.data.api.ApiClient
import com.turtle.amatda.data.api.TourAPIService
import com.turtle.amatda.data.api.WeatherAPIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {

    @Singleton
    @Provides
    @Named("WeatherRetrofit")
    fun provideWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(ApiClient.WEATHER_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("TourRetrofit")
    fun provideTourRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .baseUrl(ApiClient.TOUR_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherAPIService(@Named("WeatherRetrofit") retrofit: Retrofit): WeatherAPIService {
        return retrofit.create(WeatherAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideTourAPIService(@Named("TourRetrofit") retrofit: Retrofit): TourAPIService {
        return retrofit.create(TourAPIService::class.java)
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
            Log.d("Amatda NetModule", "request/received data to/from Server : ${message}")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}