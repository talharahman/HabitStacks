package com.example.habitstacks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_tracker_table")
data class HabitTrackerEntry(
        @ColumnInfo(name = "associated_habit")
        val associatedHabit: Long,

        @ColumnInfo(name = "current_time")
        val currentTime: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "track_location")
        var trackLocation: String,

        @ColumnInfo(name = "track_emotion")
        var trackEmotion: String,

        @ColumnInfo(name = "track_action")
        var trackAction: String) {

    @PrimaryKey(autoGenerate = true)
    var trackId: Long = 0L
}