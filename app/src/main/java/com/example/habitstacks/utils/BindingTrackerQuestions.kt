package com.example.habitstacks.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.habitstacks.model.HabitTracker

@BindingAdapter("trackerLocationText")
fun TextView.setTrackerLocationText(item: HabitTracker?) {
    item?.let {
        text = item.trackLocation
    }
}

@BindingAdapter("trackerEmotionText")
fun TextView.setTrackerEmotionText(item: HabitTracker?) {
    item?.let {
        text = item.trackEmotion
    }
}

@BindingAdapter("trackerActionText")
fun TextView.setTrackerActionText(item: HabitTracker?) {
    item?.let {
        text = item.trackAction
    }
}