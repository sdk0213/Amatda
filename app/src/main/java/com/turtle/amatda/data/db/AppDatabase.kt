package com.turtle.amatda.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.turtle.amatda.presentation.utilities.DATABASE_NAME
import com.turtle.amatda.presentation.workers.SeedDatabaseWorker
import com.turtle.amatda.data.db.item.ItemDao
import com.turtle.amatda.data.db.todo.ToDoDao
import com.turtle.amatda.data.model.todo.ItemEntity
import com.turtle.amatda.data.model.todo.TodoEntity

@Database(entities = [TodoEntity::class, ItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): ToDoDao
    abstract fun itemDao(): ItemDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            WorkManager.getInstance(context).enqueue(
                                OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            )
                        }
                    }
                )
                .fallbackToDestructiveMigration()
                .build()
        }

    }

}