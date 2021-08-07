
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