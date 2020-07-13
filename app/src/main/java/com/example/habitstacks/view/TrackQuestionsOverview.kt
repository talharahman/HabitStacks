package com.example.habitstacks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstacks.databinding.TrackQuestionsOverviewBinding
import com.example.habitstacks.R
import com.example.habitstacks.database.HabitDatabase
import com.example.habitstacks.model.HabitTrackerEntry
import com.example.habitstacks.viewmodel.TrackQuestionViewModel
import com.example.habitstacks.viewmodel.TrackQuestionViewModelFactory


class TrackQuestionsOverview : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: TrackQuestionsOverviewBinding = DataBindingUtil.inflate(
                inflater, R.layout.track_questions_overview, container, false)

        val dataSource = HabitDatabase.getInstance(requireContext()).habitDao
        val viewModelFactory = TrackQuestionViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(TrackQuestionViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.trackQuestionViewModel = viewModel

        binding.newTrackButton.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_dashBoard_to_trackQuestionsOverview)
        }

        val adapter = TrackQuestionsAdapter()
        binding.trackQuestionsOverview.adapter = adapter

//        viewModel.trackerEntries.observe(viewLifecycleOwner, Observer { list: List<HabitTrackerEntry>? ->
//            list?.let { adapter.submitList(list) }
//        })

        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trackQuestionsOverview.layoutManager = manager

       return binding.root
    }
}