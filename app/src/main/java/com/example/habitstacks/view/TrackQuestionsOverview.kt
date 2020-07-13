package com.example.habitstacks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.databinding.TrackQuestionsOverviewBinding
import com.example.habitstacks.R
import com.example.habitstacks.database.HabitDatabase
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

       return binding.root
    }
}