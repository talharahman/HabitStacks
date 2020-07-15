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
import com.example.habitstacks.database.TrackerEntryDatabase
import com.example.habitstacks.model.HabitTrackerEntry
import com.example.habitstacks.recyclerview.HabitsOverviewAdapter
import com.example.habitstacks.recyclerview.TrackQuestionsAdapter
import com.example.habitstacks.viewmodel.NewTrackerViewModelFactory
import com.example.habitstacks.viewmodel.TrackerOverviewViewModel
import com.example.habitstacks.viewmodel.TrackerOverviewViewModelFactory


class TrackQuestionsOverview : Fragment() {

    private lateinit var binding: TrackQuestionsOverviewBinding
    private lateinit var viewModel: TrackerOverviewViewModel
    private lateinit var adapter: TrackQuestionsAdapter
    private lateinit var selectedHabit: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.track_questions_overview, container, false)

        initBackend()
        initViews()
        initObservers()

        return binding.root
    }

    private fun initBackend() {
        val dataSource = TrackerEntryDatabase.getInstance(requireContext()).trackerEntryDao
        selectedHabit = TrackQuestionsOverviewArgs.fromBundle(requireArguments()).associatedHabit

        val viewModelFactory = TrackerOverviewViewModelFactory(dataSource, selectedHabit)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(TrackerOverviewViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.trackerOverviewViewModel = viewModel
    }

    private fun initViews() {
        adapter = TrackQuestionsAdapter()

        binding.newTrackButton.setOnClickListener { view: View ->
            view.findNavController().navigate(TrackQuestionsOverviewDirections
                    .actionTrackerOverViewToNewTracker(selectedHabit))
        }

        binding.trackQuestionsOverview.adapter = adapter

        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.trackQuestionsOverview.layoutManager = manager
    }

    private fun initObservers() {
        viewModel.trackerEntries.observe(viewLifecycleOwner, Observer { list: List<HabitTrackerEntry>? ->
            list?.let { adapter.submitList(list) }
        })

    }
}