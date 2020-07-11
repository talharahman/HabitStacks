package com.example.habitstacks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.habitstacks.R
import com.example.habitstacks.database.HabitDatabase
import com.example.habitstacks.databinding.HabitDashboardBinding
import com.example.habitstacks.viewmodel.DashBoardViewModelFactory
import com.example.habitstacks.viewmodel.HabitDashboardViewModel

class HabitDashboard : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: HabitDashboardBinding = DataBindingUtil.inflate(
                inflater, R.layout.habit_dashboard, container, false)

        val dataSource = HabitDatabase.getInstance(requireContext()).habitDao
        val viewModelFactory = DashBoardViewModelFactory(dataSource)
        val dashBoardViewModel = ViewModelProvider(
                this, viewModelFactory).get(HabitDashboardViewModel::class.java)

        binding.habitDashboardViewModel = dashBoardViewModel

        binding.newHabitButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_dashBoard_to_addNewHabit) }

        return binding.root
    }


}