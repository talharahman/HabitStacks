package com.example.habitstacks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits_table")
data class Habit(
        @PrimaryKey(autoGenerate = true)
        var habitId: Long = 0L,

        @ColumnInfo(name = "habit_description")
        var habitDescription: String)
