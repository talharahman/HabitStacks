package com.example.habitstacks.database

import android.content.Context
import androidx.room.*
import com.example.habitstacks.model.Habit
import com.example.habitstacks.model.HabitTrackerEntry

@Database(entities = [Habit::class], version = 2, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {

    abstract val habitDao: HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            HabitDatabase::class.java,
                            "habit_overview_database")
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
