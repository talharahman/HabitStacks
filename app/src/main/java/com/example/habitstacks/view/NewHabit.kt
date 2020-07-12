package com.example.habitstacks.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.R
import com.example.habitstacks.database.HabitDatabase
import com.example.habitstacks.databinding.NewHabitBinding
import com.example.habitstacks.viewmodel.NewHabitViewModel
import com.example.habitstacks.viewmodel.NewHabitViewModelFactory

class NewHabit : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: NewHabitBinding = DataBindingUtil.inflate(
                inflater, R.layout.new_habit, container, false)

        val dataSource = HabitDatabase.getInstance(requireContext()).habitDao
        val viewModelFactory = NewHabitViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(NewHabitViewModel::class.java)

        binding.newHabitViewModel = viewModel

        binding.editHabitDescription.addTextChangedListener {
            it?.let { viewModel.onTextChanged(it.toString()) }
        }

        return binding.root
    }
}
