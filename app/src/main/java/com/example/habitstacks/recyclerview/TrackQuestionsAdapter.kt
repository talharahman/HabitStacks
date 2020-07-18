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
import com.example.habitstacks.view.TrackQuestionsOverviewDirections
import com.example.habitstacks.viewmodel.OnEntryDeletionListener

class TrackQuestionsAdapter(private val listener: OnEntryDeletionListener) : ListAdapter<HabitTrackerEntry, TrackQuestionsAdapter.ViewHolder>(TrackDiffCallback) {

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
            binding.trackerEditButton.setOnClickListener {
                it.findNavController().navigate(TrackQuestionsOverviewDirections
                        .actionTrackQuestionsOverviewToEditTrackerFragment(item))
            }
            binding.trackerDeleteButton.setOnClickListener {
                binding.trackerOverviewCardview.visibility = View.GONE
                binding.layoutTrackDeletion.cardviewTrackDeletion.visibility = View.VISIBLE
                binding.layoutTrackDeletion.deleteNoConfirm.setOnClickListener {
                    binding.layoutTrackDeletion.cardviewTrackDeletion.visibility = View.GONE
                    binding.trackerOverviewCardview.visibility = View.VISIBLE
                }
                binding.layoutTrackDeletion.deleteYesConfirm.setOnClickListener {
                    listener.onEntryDeleted(item)
                    binding.layoutTrackDeletion.cardviewTrackDeletion.visibility = View.GONE
                    binding.trackerOverviewCardview.visibility = View.VISIBLE
                }
            }
            binding.executePendingBindings()
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
