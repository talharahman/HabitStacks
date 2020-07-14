package com.example.habitstacks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstacks.databinding.TrackQuestionsOverviewBinding
import com.example.habitstacks.R
import com.example.habitstacks.database.TrackerEntryDatabase
import com.example.habitstacks.recyclerview.TrackQuestionsAdapter
import com.example.habitstacks.viewmodel.NewTrackerViewModel
import com.example.habitstacks.viewmodel.NewTrackerViewModelFactory


class TrackQuestionsOverview : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: TrackQuestionsOverviewBinding = DataBindingUtil.inflate(
                inflater, R.layout.track_questions_overview, container, false)

        val dataSource = TrackerEntryDatabase.getInstance(requireContext()).habitTrackerDao
        val viewModelFactory = NewTrackerViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(NewTrackerViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.trackQuestionViewModel = viewModel

//        binding.newTrackButton.setOnClickListener {view: View ->
//            view.findNavController().navigate(R.id.action_dashBoard_to_trackQuestionsOverview)
//        }

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