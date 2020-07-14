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

@BindingAdapter("habitRatingIcon")
fun ImageView.setRatingIcon(item: Habit?) {
    item?.let {
        setImageResource(when (item.habitRating) {
            "POSITIVE" -> R.drawable.ic_baseline_thumb_up
            "NEUTRAL" -> R.drawable.ic_baseline_check
            "NEGATIVE" -> R.drawable.ic_baseline_thumb_down
            else -> android.R.color.transparent
        })
    }
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


