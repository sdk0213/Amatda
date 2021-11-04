package com.turtle.amatda.presentation.android.alarmmanger

import android.app.AlarmManager
import android.app.PendingIntent
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class AlarmManagerUtil @Inject constructor(
    private val alarmManager: AlarmManager,
    @Named("Alarm1") private val alarm1: PendingIntent
) {

    fun setAndAllowWhileIdle(calendar: Calendar) {

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC,
            calendar.timeInMillis,
            alarm1
        )

    }

    fun cancelAllAlarm() {
        alarmManager.cancel(alarm1)
    }


}

