package com.example.habitstacks.view

import android.os.Bundle
import android.text.Editable
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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.newHabitViewModel = viewModel

        viewModel.backButtonVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) binding.backButton.visibility = View.VISIBLE
                else binding.backButton.visibility = View.GONE
            }
        })

        viewModel.cardViewDescriptionVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutAddDescription.cardViewHabitDescription.visibility = View.VISIBLE
                    binding.layoutAddDescription.editHabitDescription.addTextChangedListener { input: Editable? ->
                        input?.let { viewModel.onTextChanged(input.toString()) }
                    }
                }
                else binding.layoutAddDescription.cardViewHabitDescription.visibility = View.GONE
            }
        })

        viewModel.cardViewPosNegVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) binding.layoutPositiveNegative.cardViewPositiveOrNegative.visibility = View.VISIBLE
                else binding.layoutPositiveNegative.cardViewPositiveOrNegative.visibility = View.GONE
            }
        })

        viewModel.cardViewPriorityVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) binding.layoutPriority.cardviewPriority.visibility = View.VISIBLE
                else binding.layoutPriority.cardviewPriority.visibility = View.GONE
            }
        })

        return binding.root
    }
}
