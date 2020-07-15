package com.example.habitstacks.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.habitstacks.model.HabitTrackerEntry

@Dao
interface TrackerEntryDao {

    @Insert
    fun insert(trackerEntryEntry: HabitTrackerEntry)

    @Query("SELECT * from habit_tracker_table WHERE trackId = :key")
    fun get(key: Long): HabitTrackerEntry

    @Query("SELECT * from habit_tracker_table WHERE associated_habit = :key")
    fun getEntriesForHabit(key: String): List<HabitTrackerEntry>
}