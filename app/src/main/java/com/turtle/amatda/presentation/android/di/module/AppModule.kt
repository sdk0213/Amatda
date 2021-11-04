package com.turtle.amatda.presentation.android.di.module

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.WorkManager
import com.google.android.gms.location.GeofencingClient
import com.turtle.amatda.presentation.android.AndroidUtil
import com.turtle.amatda.presentation.android.App
import com.turtle.amatda.presentation.android.alarmmanger.AlarmManagerUtil
import com.turtle.amatda.presentation.android.di.qualifier.ApplicationContext
import com.turtle.amatda.presentation.android.geofence.GeofenceUtil
import com.turtle.amatda.presentation.android.notification.NotificationUtil
import com.turtle.amatda.presentation.android.service.ServiceUtil
import com.turtle.amatda.presentation.android.service.StartWorkerService
import com.turtle.amatda.presentation.utilities.CatchUnexpectedException
import com.turtle.amatda.presentation.utilities.CustomTimberDebug
import dagger.Module
import dagger.Provides
import javax.inject.Named
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

    @Provides
    fun provideCustomTimberDebug(): CustomTimberDebug {
        return CustomTimberDebug()
    }

    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    // 예상치 못한 에러 체크
    @Provides
    fun provideCatchException(
        @ApplicationContext context: Context,
        androidUtil: AndroidUtil
    ): CatchUnexpectedException {
        return CatchUnexpectedException(context, androidUtil)
    }

    // 안드로이드 관련 Util
    @Provides
    @Singleton
    fun provideAndroidUtil(@ApplicationContext context: Context): AndroidUtil {
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
    fun provideNotificationUtil(
        notificationManager: NotificationManager,
        @ApplicationContext context: Context
    ): NotificationUtil {
        return NotificationUtil(
            notificationManager,
            context
        )
    }

    // 지오펜스 관련 유틸
    @Provides
    @Singleton
    fun provideGeofenceUtil(
        @ApplicationContext context: Context,
        geofencingClient: GeofencingClient,
        @Named("GeoPendingIntent")
        geofencePendingIntent: PendingIntent
    ): GeofenceUtil {
        return GeofenceUtil(
            geofencingClient,
            geofencePendingIntent
        )
    }

    // 서비스 관련 유틸
    @Provides
    @Singleton
    fun provideServiceUtil(
        @ApplicationContext context: Context,
        androidUtil: AndroidUtil
    ): ServiceUtil {
        return ServiceUtil(
            context,
            androidUtil
        )
    }

    // 알람매니저
    @Provides
    @Singleton
    fun provideAlarmManager(
        @ApplicationContext context: Context
    ): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }


    // 알람매니저 유틸 클래스
    @Provides
    @Singleton
    fun provideAlarmManagerUtil(
        alarmManager: AlarmManager,
        @Named("Alarm1") pendingIntent: PendingIntent
    ): AlarmManagerUtil {
        return AlarmManagerUtil(alarmManager, pendingIntent)
    }


    // 알람 팬딩 인텐트 (지오펜싱 서비스 알람예약)
    @Provides
    @Named("Alarm1")
    fun provideAlarmPendingIntent(
        @ApplicationContext context: Context
    ): PendingIntent {
        return PendingIntent.getService(
            context,
            0,
            Intent(context, StartWorkerService::class.java),
            if (Build.VERSION_CODES.S <= Build.VERSION.SDK_INT)
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            else
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}