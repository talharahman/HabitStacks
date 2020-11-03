package com.example.habitstacks.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.R
import com.example.habitstacks.database.HabitDatabase
import com.example.habitstacks.databinding.HabitCalendarBinding
import com.example.habitstacks.viewmodel.HabitCalendarViewModel
import com.example.habitstacks.viewmodel.HabitCalendarViewModelFactory

class HabitCalendarFragment : Fragment() {

    private lateinit var binding: HabitCalendarBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.habit_calendar, container, false)
        initBackend()
        return binding.root
    }

    private fun initBackend() {
        val dataSource = HabitDatabase.getInstance(requireContext()).habitDao
        val selectedHabit = HabitCalendarFragmentArgs.fromBundle(requireArguments()).selectedHabit
        val application = requireNotNull(this.activity).application
        val factory = HabitCalendarViewModelFactory(dataSource, selectedHabit, application)
        val viewModel = ViewModelProvider(this, factory)
                .get(HabitCalendarViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.habitCalendarViewModel = viewModel

        initViews()
    }

    private fun initViews() {
        binding.calendarView.setOnDateChangeListener { cv, year, month, day ->
            binding.dateNote.hint = "Notes for ${month + 1} / ${day} / $year"
        }
    }
}