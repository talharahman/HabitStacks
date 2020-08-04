package com.example.habitstacks.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "habits_table")
data class Habit(
        @ColumnInfo(name = "habit_description")
        var habitDescription: String,

        @ColumnInfo(name = "habit_rating")
        var habitDuration: String,

        @ColumnInfo(name = "habit_priority")
        var habitPriority: String) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var habitId: Long = 0L
}

enum class Duration { DAILY, WEEKLY, MONTHLY }

enum class Priority { LOW, MEDIUM, HIGH }
