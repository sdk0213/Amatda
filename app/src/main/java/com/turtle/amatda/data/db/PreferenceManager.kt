package com.turtle.amatda.data.db

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(private val context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(RESERVATION_APP, Context.MODE_PRIVATE)

    var uuid: String
        get() = sharedPref.getString(UUID_KEY, "").toString()
        set(value) {
            val editor = sharedPref.edit()
            editor.putString(UUID_KEY, value)
            editor.apply()
        }

    var alarmSetting: Boolean
        get() = sharedPref.getBoolean(ALARM_SETTING_KEY, true)
        set(value) {
            val editor = sharedPref.edit()
            editor.putBoolean(ALARM_SETTING_KEY, value)
            editor.apply()
        }

    companion object {
        private const val RESERVATION_APP = "AMATDA_APP"
        private const val UUID_KEY = "UUID_KEY"
        private const val ALARM_SETTING_KEY = "ALARM_SETTING_KEY"
    }
}