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
}

//data class

enum class Rating { POSITIVE, NEUTRAL, NEGATIVE }

enum class Priority { LOW, MEDIUM, HIGH }
