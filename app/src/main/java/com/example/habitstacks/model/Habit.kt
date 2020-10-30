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

        @ColumnInfo(name = "habit_duration")
        var habitDuration: String,

        @ColumnInfo(name = "duration_frequency")
        var durationFrequency: Int,

        @ColumnInfo(name = "habit_rating")
        var habitRating: String) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var habitId: Long = 0L

    @IgnoredOnParcel
    @ColumnInfo(name = "frequency_count")
    var frequencyCount = 0
}

enum class Duration { DAILY, WEEKLY, MONTHLY }

enum class Rating { POSITIVE, NEUTRAL, NEGATIVE }
