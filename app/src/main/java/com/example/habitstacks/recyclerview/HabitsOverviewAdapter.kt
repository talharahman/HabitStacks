package com.example.habitstacks.recyclerview

import android.util.Log
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
import com.example.habitstacks.view.ProgressUpdateListener

class HabitsOverviewAdapter(private val deletionListener: OnHabitDeletionListener,
                            private val updateListener: ProgressUpdateListener) : ListAdapter<Habit,
        HabitsOverviewAdapter.ViewHolder>(HabitDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), deletionListener, updateListener)
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

        fun bind(item: Habit,
                 deletionListener: OnHabitDeletionListener,
                 updateListener: ProgressUpdateListener) {
            binding.habit = item
            binding.trackButton.setOnClickListener{ it.onClick(item, deletionListener, updateListener) }
            binding.editHabitButton.setOnClickListener { it.onClick(item, deletionListener, updateListener) }
            binding.calendarButton.setOnClickListener { it.onClick(item, deletionListener, updateListener) }
            binding.deleteHabitButton.setOnClickListener { it.onClick(item, deletionListener, updateListener) }
            binding.thumbsUp.setOnClickListener { it.onClick(item, deletionListener, updateListener) }
            binding.thumbsDown.setOnClickListener { it.onClick(item, deletionListener, updateListener) }
            binding.executePendingBindings()
        }

        private fun View.onClick(item: Habit,
                                 deletionListener: OnHabitDeletionListener,
                                 updateListener: ProgressUpdateListener) {
            when (this) {
                binding.trackButton -> {
                    this.findNavController().navigate(HabitDashboardDirections
                            .actionDashBoardToTrackQuestionsOverview(item.habitDescription))
                }
                binding.editHabitButton -> {
                    this.findNavController().navigate(HabitDashboardDirections
                            .actionDashBoardToEditHabitFragment(item))
                }
                binding.calendarButton -> {
                    this.findNavController().navigate(HabitDashboardDirections
                            .actionDashBoardToHabitCalendarFragment(item))
                }
                binding.thumbsUp -> {
                    updateListener.onFrequencyCountChanged(item,true)
                    binding.frequencyView.setHabitFrequencyText(item)
                }
                binding.thumbsDown -> {
                    updateListener.onFrequencyCountChanged(item, false)
                    binding.frequencyView.setHabitFrequencyText(item)
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
                        deletionListener.onHabitDeleted(item)
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

