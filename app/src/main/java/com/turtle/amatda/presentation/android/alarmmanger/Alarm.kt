package com.turtle.amatda.presentation.android.alarmmanger

import java.util.*

// 임시 코드
interface ManageAlarm {
    fun setAlarm(afterSeconds: Int): Boolean
    fun cancelAlarm(): Boolean
}

abstract class Alarm(){

    fun makeCalender(seconds: Int) : Calendar {
        return Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.SECOND, seconds)
        }
    }

}

class DozeModeAlarm<T> constructor(
    private val componentClass : Class<T>
): Alarm(), ManageAlarm {

    override fun setAlarm(afterSeconds: Int): Boolean {
        makeCalender(afterSeconds)
        return false
    }

    override fun cancelAlarm(): Boolean {
        return false
    }
}

class NormalAlarm : ManageAlarm {

    override fun setAlarm(afterSeconds: Int): Boolean {
        return false
    }

    override fun cancelAlarm(): Boolean {
        return false
    }
}