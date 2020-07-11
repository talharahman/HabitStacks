package com.example.habitstacks.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habitstacks.model.Habit

@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {

    abstract val habitDao: HabitDao

    companion object {
        /**
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            /**
             * Multiple threads can ask for the database at the same time,
             * ensure we only initialize it once by using synchronized.
             * Only one thread may enter a synchronized block at a time.
             */
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            HabitDatabase::class.java,
                            "habit_tracking_database")
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}