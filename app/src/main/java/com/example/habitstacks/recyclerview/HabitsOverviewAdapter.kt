package com.example.habitstacks.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstacks.databinding.HabitItemviewBinding
import com.example.habitstacks.model.Habit
import com.example.habitstacks.view.HabitDashboardDirections
import com.example.habitstacks.view.OnHabitDeletionListener

class HabitsOverviewAdapter(private val listener: OnHabitDeletionListener) : ListAdapter<Habit,
        HabitsOverviewAdapter.ViewHolder>(HabitDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
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

        fun bind(item: Habit, listener: OnHabitDeletionListener) {
            binding.habit = item
            binding.trackButton.setOnClickListener { it.onClick(item, listener) }
            binding.editHabitButton.setOnClickListener { it.onClick(item, listener) }
            binding.deleteHabitButton.setOnClickListener { it.onClick(item, listener) }
            binding.executePendingBindings()
        }

        private fun View.onClick(data: Habit, sender: OnHabitDeletionListener) {
            when (this) {
                binding.trackButton -> {
                    this.findNavController().navigate(HabitDashboardDirections
                            .actionDashBoardToTrackQuestionsOverview(data.habitDescription))
                }
                binding.editHabitButton -> {
                    this.findNavController().navigate(HabitDashboardDirections
                            .actionDashBoardToEditHabitFragment(data))
                }
                binding.deleteHabitButton -> {
                    val overview = binding.habitOverviewCardview
                    val deletion = binding.layoutHabitDeletion.cardviewHabitDeletion
                    overview.visibility = View.GONE
                    deletion.visibility = View.VISIBLE

                    binding.layoutHabitDeletion.habitCancelDelete.setOnClickListener {
                        deletion.visibility = View.GONE
                        overview.visibility = View.VISIBLE
                    }
                    binding.layoutHabitDeletion.habitDeleteButton.setOnClickListener {
                        sender.onHabitDeleted(data)
                        deletion.visibility = View.GONE
                        overview.visibility = View.VISIBLE
                    }
                }
            }
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

