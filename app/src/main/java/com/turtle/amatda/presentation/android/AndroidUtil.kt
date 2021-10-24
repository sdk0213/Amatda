package com.turtle.amatda.presentation.android

import android.app.ActivityManager
import android.content.Context
import com.turtle.amatda.presentation.android.di.qualifier.ApplicationContext
import javax.inject.Inject

class AndroidUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}