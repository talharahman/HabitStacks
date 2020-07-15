package com.example.habitstacks.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habitstacks.model.HabitTrackerEntry

@Database(entities = [HabitTrackerEntry::class], version = 1, exportSchema = false)
abstract class TrackerEntryDatabase : RoomDatabase() {

    abstract val trackerEntryDao: TrackerEntryDao

    companion object {
        @Volatile
        private var INSTANCE: TrackerEntryDatabase? = null

        fun getInstance(context: Context): TrackerEntryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            TrackerEntryDatabase::class.java,
                            "habit_tracker_database")
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}