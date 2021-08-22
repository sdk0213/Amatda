
package com.turtle.amatda.presentation.utilities.extensions

import android.text.Editable
import java.text.SimpleDateFormat
import java.util.*

fun Date.convertDateToStringTimeStamp(): String =
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(this)

fun Date.convertDateToStringHMSTimeStamp(): String =
    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(this)

fun String.toEditable(): Editable =
    Editable.Factory.getInstance().newEditable(this)

fun CharSequence.toEditable(): Editable =
    Editable.Factory.getInstance().newEditable(this)

fun String.convertYYYYMMDD() : String =
    SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(
        SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(this)!!
    )

fun String.convertHHmm() : String =
    SimpleDateFormat("HH시 mm분", Locale.getDefault()).format(
        SimpleDateFormat("HHmm", Locale.getDefault()).parse(this)!!
    )