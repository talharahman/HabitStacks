package com.example.habitstacks.recyclerview

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.habitstacks.model.HabitTrackerEntry

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