package com.turtle.amatda.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.turtle.amatda.data.db.dao.*
import com.turtle.amatda.data.model.*
import com.turtle.amatda.presentation.utilities.DATABASE_NAME
import com.turtle.amatda.presentation.android.workmanager.SeedDatabaseWorker

@Database(
    entities = [
        CarrierEntity::class,
        ItemEntity::class,
        PocketEntity::class,
        TripEntity::class,
        TripZoneEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    DateTypeConverter::class,
    TripConceptEnumTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): CarrierDao
    abstract fun itemDao(): ItemDao
    abstract fun pocketDao(): PocketDao
    abstract fun TripDao(): TripDao
    abstract fun TripZoneDao(): TripZoneDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

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