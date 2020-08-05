package com.example.habitstacks.recyclerview

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.habitstacks.model.Habit
import com.example.habitstacks.R

@BindingAdapter("habitDescriptionText")
fun TextView.setHabitDescriptionText(item: Habit?) {
    item?.let { text = item.habitDescription }
}

@BindingAdapter("habitFrequencyText")
fun TextView.setHabitFrequency(item: Habit?) {
    item?.let { text = "0 / ${item.durationFrequency} times" }
}

@BindingAdapter("habitDurationText")
fun TextView.setHabitDurationText(item: Habit?) {
    item?.let { text = item.habitDuration.toLowerCase() }
}


@BindingAdapter("lowPriority")
fun ImageView.setLowPriorityIcon(item: Habit?) {
    item?.let {
        setImageResource(R.drawable.ic_baseline_priority)
    }
}

@BindingAdapter("medPriority")
fun ImageView.setMedIcon(item: Habit?) {
    item?.let {
        setImageResource(when (item.habitPriority) {
            "MEDIUM", "HIGH" -> R.drawable.ic_baseline_priority
            else -> android.R.color.transparent
        })
    }
}

@BindingAdapter("highPriority")
fun ImageView.setHighIcon(item: Habit?) {
    item?.let {
        setImageResource(when (item.habitPriority) {
            "HIGH" -> R.drawable.ic_baseline_priority
            else -> android.R.color.transparent
        })
    }
}


