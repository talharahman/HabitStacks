package com.example.habitstacks.recyclerview

import android.content.res.Resources
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.habitstacks.model.Habit
import com.example.habitstacks.R
import com.example.habitstacks.model.Rating
import com.google.android.material.card.MaterialCardView

@BindingAdapter("habitDescriptionText")
fun TextView.setHabitDescriptionText(item: Habit?) {
    item?.let { text = item.habitDescription }
}

@BindingAdapter("habitFrequencyText")
fun TextView.setHabitFrequencyText(item: Habit?) {
    item?.let {
        text = "${item.frequencyCount} / ${item.durationFrequency} times"
        if (item.frequencyCount > item.durationFrequency) {
            if (item.habitRating == Rating.POSITIVE.name) this.setTextColor(Color.GREEN)
            if (item.habitRating == Rating.NEGATIVE.name) this.setTextColor(Color.RED)
        } else this.setTextColor(Color.BLACK)
    }
}

@BindingAdapter("habitDurationText")
fun TextView.setHabitDurationText(item: Habit?) {
    item?.let { text = item.habitDuration.toLowerCase() }
}

@BindingAdapter("borderColor")
fun MaterialCardView.setStrokeColor(item: Habit?) {
    item?.let {
        if (item.habitRating == Rating.POSITIVE.name)
            this.strokeColor = Color.GREEN
        if (item.habitRating == Rating.NEGATIVE.name)
            this.strokeColor = Color.RED
    }
}

//@BindingAdapter("lowPriority")
//fun ImageView.setLowPriorityIcon(item: Habit?) {
//    item?.let {
//        setImageResource(R.drawable.ic_baseline_priority)
//    }
//}
//
//@BindingAdapter("medPriority")
//fun ImageView.setMedIcon(item: Habit?) {
//    item?.let {
//        setImageResource(when (item.habitRating) {
//            "MEDIUM", "HIGH" -> R.drawable.ic_baseline_priority
//            else -> android.R.color.transparent
//        })
//    }
//}
//
//@BindingAdapter("highPriority")
//fun ImageView.setHighIcon(item: Habit?) {
//    item?.let {
//        setImageResource(when (item.habitRating) {
//            "HIGH" -> R.drawable.ic_baseline_priority
//            else -> android.R.color.transparent
//        })
//    }
//}


