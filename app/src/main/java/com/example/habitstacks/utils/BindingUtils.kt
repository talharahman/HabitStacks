package com.example.habitstacks.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.habitstacks.model.Habit

@BindingAdapter("habitDescription")
fun TextView.setHabitDescription(item: Habit?) {
    item?.let { text = item.habitDescription }
}