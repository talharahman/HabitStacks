package com.example.habitstacks.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "habit_tracker_table")
data class HabitTrackerEntry(
        @ColumnInfo(name = "associated_habit")
        val associatedHabit: String,

        @ColumnInfo(name = "current_time")
        val currentTime: Long,

        @ColumnInfo(name = "track_location")
        var trackLocation: String,

        @ColumnInfo(name = "track_emotion")
        var trackEmotion: String,

        @ColumnInfo(name = "track_action")
        var trackAction: String) : Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var trackId: Long = 0L

}