package com.example.habitstacks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits_table")
data class Habit(
        @ColumnInfo(name = "habit_description")
        var habitDescription: String,

        @ColumnInfo(name = "habit_rating")
        var habitRating: String,

        @ColumnInfo(name = "habit_priority")
        var habitPriority: String) {

        @PrimaryKey(autoGenerate = true)
        var habitId: Long = 0L

        @ColumnInfo(name = "habit_tracker_entries")
        var trackEntries: MutableList<HabitTracker?> = mutableListOf(null)
}

@Entity(tableName = "habit_tracker_table")
data class HabitTracker(
        @ColumnInfo(name = "track_location")
        var trackLocation: String,

        @ColumnInfo(name = "track_emotion")
        var trackEmotion: String,

        @ColumnInfo(name = "track_action")
        var trackAction: String) {

        @PrimaryKey(autoGenerate = true)
        var trackId: Long = 0L
}

enum class Rating { POSITIVE, NEUTRAL, NEGATIVE }

enum class Priority { LOW, MEDIUM, HIGH }
