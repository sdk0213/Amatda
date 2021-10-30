package com.turtle.amatda.presentation.utilities.extensions

import android.text.Editable
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

fun Date.convertDateToStringTimeStamp(): String =
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(this)

fun Date.convertDateToStringHMSTimeStamp(): String =
    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(this)

fun Date.convertDateToStringyyyyMMddTimeStamp(): String =
    SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(this)

fun Date.convertDateToStringyyMMddTimeStampWithSlash(): String =
    SimpleDateFormat("yy/MM/dd", Locale.getDefault()).format(this)

fun Date.convertDateToStringMMddHHmmTimeStamp(): String =
    SimpleDateFormat("MM/dd HH:mm", Locale.getDefault()).format(this)

fun Date.convertDateToStringHHmmTimeStamp(): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)

fun Date.toCalenderDay(): CalendarDay {
    val cal = Calendar.getInstance(Locale.getDefault())
    cal.time = this
    return CalendarDay.from(
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH) + 1,
        cal.get(Calendar.DAY_OF_MONTH)
    )
}

fun Date.getCalendarWithoutTime() : Calendar {
    val date = this
    return GregorianCalendar().apply {
        time = date
        set(Calendar.HOUR, 0);
        set(Calendar.HOUR_OF_DAY, 0);
        set(Calendar.MINUTE, 0);
        set(Calendar.SECOND, 0);
        set(Calendar.MILLISECOND, 0);
    }
}

fun String.toEditable(): Editable =
    Editable.Factory.getInstance().newEditable(this)

fun CharSequence.toEditable(): Editable =
    Editable.Factory.getInstance().newEditable(this)

fun String.convertToDateyyyyMMddHHmm(): Date =
    SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault()).parse(this)!!

fun String.convertToDateyyyyMMdd(): Date =
    SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).parse(this)!!

fun String.convertToDateHHmm(): Date =
    SimpleDateFormat("HHmm", Locale.getDefault()).parse(this)!!

fun String.convertYYYYMMDD(): String =
    SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(
        SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(this)!!
    )

fun String.convertHHmm(): String =
    SimpleDateFormat("HH시 mm분", Locale.getDefault()).format(
        SimpleDateFormat("HHmm", Locale.getDefault()).parse(this)!!
    )

fun Date.getCountDay(endDate: Date): Long =
    ((this.time - endDate.time) / (24 * 60 * 60 * 1000)) + 1