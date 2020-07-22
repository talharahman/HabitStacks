package com.example.habitstacks.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstacks.databinding.TrackQuestionsItemviewBinding
import com.example.habitstacks.model.HabitTrackerEntry
import com.example.habitstacks.view.OnEntryDeletionListener
import com.example.habitstacks.view.TrackQuestionsOverviewDirections

class TrackQuestionsAdapter(private val listener: OnEntryDeletionListener) :
        ListAdapter<HabitTrackerEntry, TrackQuestionsAdapter.ViewHolder>(TrackDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, listener)
    }

    companion object TrackDiffCallback : DiffUtil.ItemCallback<HabitTrackerEntry>() {

        override fun areItemsTheSame(oldItem: HabitTrackerEntry, newItem: HabitTrackerEntry): Boolean {
            return oldItem.trackId == newItem.trackId
        }

        override fun areContentsTheSame(oldItem: HabitTrackerEntry, newItem: HabitTrackerEntry): Boolean {
            return oldItem == newItem
        }
    }


    class ViewHolder(private val binding: TrackQuestionsItemviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: HabitTrackerEntry, listener: OnEntryDeletionListener) {
            binding.habitTracker = item
            binding.trackerEditButton.setOnClickListener { it.onClick(item, listener) }
            binding.trackerDeleteButton.setOnClickListener { it.onClick(item, listener) }
            binding.executePendingBindings()
        }

        private fun View.onClick(data: HabitTrackerEntry, sender: OnEntryDeletionListener) {
            when (this) {
                binding.trackerEditButton -> {
                    this.findNavController().navigate(TrackQuestionsOverviewDirections
                            .actionTrackQuestionsOverviewToEditTrackerFragment(data))
                }
                binding.trackerDeleteButton -> {
                    val overview = binding.trackerOverviewCardview
                    val deletion = binding.layoutTrackDeletion.cardviewTrackDeletion
                    overview.visibility = View.GONE
                    deletion.visibility = View.VISIBLE

                    binding.layoutTrackDeletion.deleteNoConfirm.setOnClickListener {
                        deletion.visibility = View.GONE
                        overview.visibility = View.VISIBLE
                    }

                    binding.layoutTrackDeletion.deleteYesConfirm.setOnClickListener {
                        sender.onEntryDeleted(data)
                        deletion.visibility = View.GONE
                        overview.visibility = View.VISIBLE
                    }
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TrackQuestionsItemviewBinding.inflate(
                        layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}




