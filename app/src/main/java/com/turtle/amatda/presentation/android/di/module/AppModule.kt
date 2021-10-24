package com.turtle.amatda.presentation.android.di.module

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.turtle.amatda.presentation.android.AndroidUtil
import com.turtle.amatda.presentation.android.App
import com.turtle.amatda.presentation.android.di.qualifier.ApplicationContext
import com.turtle.amatda.presentation.android.notification.NotificationUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideApp(app: App): Application = app

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(app: App): Context = app

    // 안드로이드 관련 Util
    @Provides
    @Singleton
    fun provideAndroidUtil(@ApplicationContext context: Context) : AndroidUtil{
        return AndroidUtil(context)
    }

    // 노티피케이션(알림) 매니저
    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
    }

    // 노티피케이션(알림) 유틸
    @Provides
    @Singleton
    fun provideNotiUtil(notificationManager: NotificationManager) : NotificationUtil {
        return NotificationUtil(notificationManager)
    }

}