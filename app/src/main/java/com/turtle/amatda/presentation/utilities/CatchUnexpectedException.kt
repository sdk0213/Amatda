package com.turtle.amatda.presentation.utilities

import android.content.Context
import com.turtle.amatda.presentation.android.di.qualifier.ApplicationContext
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CatchUnexpectedException @Inject constructor(
    @ApplicationContext private val context: Context
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val out = ByteArrayOutputStream()
        throwable.printStackTrace(PrintStream(out))
        saveRecord("Unexpected Exception 발생\n$out")
    }

    private fun saveRecord(str: String) {

        val saveFile = File(
            context.filesDir, "exception.txt"
        )

        try {
            val now = System.currentTimeMillis() // 현재시간 받아오기
            val date = Date(now) // Date 객체 생성
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val nowTime: String = sdf.format(date)
            val buf = BufferedWriter(FileWriter(saveFile, true))
            buf.append("$nowTime ") // 날짜 쓰기
            buf.append(str) // 파일 쓰기
            buf.newLine() // 개행
            buf.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}