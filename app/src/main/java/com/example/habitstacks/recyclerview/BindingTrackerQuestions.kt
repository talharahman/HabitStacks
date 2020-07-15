package com.example.habitstacks.recyclerview

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.habitstacks.model.HabitTrackerEntry
import java.text.SimpleDateFormat

@BindingAdapter("trackerEntryTime")
fun TextView.setTrackerEntryTime(item: HabitTrackerEntry?) {
    item?.let {
        text = convertLongToDateString(item.currentTime)
    }
}

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' 'HH:mm")
            .format(systemTime).toString()
}

@BindingAdapter("trackerLocationText")
fun TextView.setTrackerLocationText(item: HabitTrackerEntry?) {
    item?.let {
        text = item.trackLocation
    }
}

@BindingAdapter("trackerEmotionText")
fun TextView.setTrackerEmotionText(item: HabitTrackerEntry?) {
    item?.let {
        text = item.trackEmotion
    }
}

@BindingAdapter("trackerActionText")
fun TextView.setTrackerActionText(item: HabitTrackerEntry?) {
    item?.let {
        text = item.trackAction
    }
}