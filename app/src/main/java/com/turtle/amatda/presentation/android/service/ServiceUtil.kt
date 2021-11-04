package com.turtle.amatda.presentation.android.service

import android.content.Context
import android.content.Intent
import android.os.Build
import com.turtle.amatda.presentation.android.AndroidUtil
import com.turtle.amatda.presentation.android.di.qualifier.ApplicationContext
import com.turtle.amatda.presentation.utilities.intentServiceText
import com.turtle.amatda.presentation.utilities.intentServiceTitle
import com.turtle.amatda.presentation.utilities.thisServiceIsForeGroundService
import timber.log.Timber
import javax.inject.Inject

class ServiceUtil @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val androidUtil: AndroidUtil,
) {

    fun startService(
        cls: Class<*>,
        title: String = "",
        text: String = ""
    ): Boolean {
        try {
            if (!androidUtil.isServiceRunning(GeofenceReceiverService::class.java)) {
                Timber.d("startService: ${cls.name}")
                androidUtil.saveLog("${cls.name} start")
                if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                    context.startForegroundService(
                        Intent(
                            context,
                            cls
                        ).apply {
                            putExtra(thisServiceIsForeGroundService, true)
                            if (title.isNotEmpty()) {
                                putExtra(intentServiceTitle, title)
                            }
                            if (text.isNotEmpty()) {
                                putExtra(intentServiceText, text)
                            }
                        }
                    )
                } else {
                    context.startService(
                        Intent(
                            context,
                            cls
                        )
                    )
                }
            } else {
                Timber.d("${cls.name} is already exist")
            }
            return true
        } catch (ex: Exception) {
            Timber.e("Error executing start ${cls.name} exception is : $ex")
            return false
        }
    }

    fun stopService(cls: Class<*>) {
        androidUtil.saveLog("stopService: ${cls.name}")
        context.stopService(
            Intent(
                context,
                cls
            )
        )
    }

}