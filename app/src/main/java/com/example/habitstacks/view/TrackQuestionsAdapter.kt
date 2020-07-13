package com.example.habitstacks.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstacks.databinding.TrackQuestionsItemviewBinding
import com.example.habitstacks.model.HabitTracker

class TrackQuestionsAdapter : ListAdapter<HabitTracker, TrackQuestionsAdapter.ViewHolder>(TrackDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    class ViewHolder(private var binding: TrackQuestionsItemviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HabitTracker) {
            binding.habitTracker = item
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

class TrackDiffCallback : DiffUtil.ItemCallback<HabitTracker>() {

    override fun areItemsTheSame(oldItem: HabitTracker, newItem: HabitTracker): Boolean {
        return oldItem.trackId == newItem.trackId
    }

    override fun areContentsTheSame(oldItem: HabitTracker, newItem: HabitTracker): Boolean {
        return oldItem == newItem
    }

}