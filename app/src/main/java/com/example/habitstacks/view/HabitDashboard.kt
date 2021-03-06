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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habitstacks.R
import com.example.habitstacks.database.HabitDatabase
import com.example.habitstacks.databinding.HabitDashboardBinding
import com.example.habitstacks.model.Habit
import com.example.habitstacks.recyclerview.HabitsOverviewAdapter
import com.example.habitstacks.viewmodel.DashBoardViewModelFactory
import com.example.habitstacks.viewmodel.HabitDashboardViewModel

class HabitDashboard : Fragment() {

    private lateinit var dashBoardViewModel: HabitDashboardViewModel
    private lateinit var binding: HabitDashboardBinding
    private lateinit var adapter: HabitsOverviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.habit_dashboard, container, false)

        initBackend()
        initViews()
        initObservers()

        return binding.root
    }

    private fun initBackend() {
        val application = requireNotNull(this.activity).application
        val dataSource = HabitDatabase
                .getInstance(application)
                .habitDao
        val viewModelFactory = DashBoardViewModelFactory(dataSource, application)
        dashBoardViewModel = ViewModelProvider(this, viewModelFactory)
                .get(HabitDashboardViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.habitDashboardViewModel = dashBoardViewModel
    }

    private fun initViews() {
        adapter = HabitsOverviewAdapter(
        object : OnHabitDeletionListener {
            override fun onHabitDeleted(habit: Habit) {
                dashBoardViewModel.deleteSelectedHabit(habit)
            }
        }, object : ProgressUpdateListener {
            override fun onFrequencyCountChanged(item: Habit, increment: Boolean) {
                dashBoardViewModel.updateFrequencyCount(item, increment)
            }
        })

        binding.newHabitButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_dashBoard_to_addNewHabit)
        }

        binding.habitsOverview.adapter = adapter

        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.habitsOverview.layoutManager = manager

    }

    private fun initObservers() {
        dashBoardViewModel.habits.observe(viewLifecycleOwner, Observer { list: List<Habit>? ->
            list?.let { adapter.submitList(list) }
        })

    }

}

interface OnHabitDeletionListener {
    fun onHabitDeleted(habit: Habit)
}

interface ProgressUpdateListener {
    fun onFrequencyCountChanged(item: Habit, increment: Boolean)
}