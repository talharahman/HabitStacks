package com.example.habitstacks.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstacks.databinding.HabitItemviewBinding
import com.example.habitstacks.model.Habit

class HabitsOverviewAdapter : ListAdapter<Habit, HabitsOverviewAdapter.ViewHolder>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class ViewHolder(private var binding: HabitItemviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Habit) {
            binding.habit = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HabitItemviewBinding
                        .inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {

    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.habitId == newItem.habitId
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }
}