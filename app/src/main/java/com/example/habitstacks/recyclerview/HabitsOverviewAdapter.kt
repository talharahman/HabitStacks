package com.example.habitstacks.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstacks.databinding.HabitItemviewBinding
import com.example.habitstacks.model.Habit
import com.example.habitstacks.view.HabitDashboardDirections

class HabitsOverviewAdapter : ListAdapter<Habit,
        HabitsOverviewAdapter.ViewHolder>(HabitDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {

        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.habitId == newItem.habitId
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: HabitItemviewBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Habit) {
            binding.habit = item
            binding.trackButton.setOnClickListener {
                it.findNavController().navigate(HabitDashboardDirections
                        .actionDashBoardToTrackQuestionsOverview(item.habitDescription))
            }
            binding.editHabitButton.setOnClickListener {
                it.findNavController().navigate(HabitDashboardDirections
                        .actionDashBoardToEditHabitFragment(item))
            }
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

