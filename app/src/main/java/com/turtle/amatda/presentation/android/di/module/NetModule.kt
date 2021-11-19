package com.turtle.amatda.presentation.android.di.module

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.gson.GsonBuilder
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import com.turtle.amatda.R
import com.turtle.amatda.data.api.ApiClient
import com.turtle.amatda.data.api.FirebaseAuthApiService
import com.turtle.amatda.data.api.TourAPIService
import com.turtle.amatda.data.api.WeatherAPIService
import com.turtle.amatda.presentation.android.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
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
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder().exceptionOnUnreadXml(false).build()
                )
            )
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
            .connectTimeout(40, TimeUnit.SECONDS)  // 커넥션 타임아웃
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    // 로그를 찍기 위한 로깅 인터셉터 설정
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Timber.d("Http Logging message : $message")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    // 구글 로그인 옵션
    @Provides
    @Singleton
    fun provideGoogleSignInOptions(@ApplicationContext context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // R.string.default_web_client_id -> google-services.json 을 삽입할경우 만들어지는 값
            .requestEmail()
            .build()
    }

    // 구글 로그인 클라이언트
    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context, googleSignInOptions: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, googleSignInOptions)
    }

    // 구글 파이어 베이스 인증
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    // 구글 파이어베이스 API
    @Provides
    @Singleton
    fun provideFirebaseAuthApiService(firebaseAuth: FirebaseAuth): FirebaseAuthApiService {
        return FirebaseAuthApiService(firebaseAuth)
    }

    // 구글 파이어베이스 파이어스토어(Firebase FireStore) API
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    // 구글 파이어베이스 스토리지 (Firebase Stroage) API
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

}