package com.turtle.amatda.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToCheck::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toCheckDao() : ToCheckDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "amatda.db").fallbackToDestructiveMigration().build()
        }
    }
}