package com.turtle.amatda.presentation.android.alarmmanger

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.turtle.amatda.presentation.android.broadcast.BootUpReceiver
import com.turtle.amatda.presentation.utilities.broadCastMessageOfUndeadService
import java.util.*
import javax.inject.Inject

// 임시 코드
class AlarmManagerUtil @Inject constructor(
    private val context: Context,
) {

    fun setAlarm(afterSeconds: Int){
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, afterSeconds)

        val intent = Intent(context, BootUpReceiver::class.java).apply {
            action = broadCastMessageOfUndeadService
        }

        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
            else -> {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        }

    }


}

