package com.example.habitstacks.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.habitstacks.databinding.NewEditHabitBinding
import com.example.habitstacks.R
import com.example.habitstacks.database.HabitDatabase
import com.example.habitstacks.model.Habit
import com.example.habitstacks.model.Priority
import com.example.habitstacks.model.Rating
import com.example.habitstacks.viewmodel.NewHabitViewModel
import com.example.habitstacks.viewmodel.NewHabitViewModelFactory

class EditHabitFragment : Fragment() {

    private lateinit var binding: NewEditHabitBinding
    private lateinit var viewModel: NewHabitViewModel
    private lateinit var selectedHabit: Habit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.new_edit_habit, container, false)

        initBackend()
        initObservers()

        return binding.root
    }

    private fun initBackend() {
        val dataSource = HabitDatabase
                .getInstance(requireContext())
                .habitDao
        selectedHabit = EditHabitFragmentArgs.fromBundle(requireArguments()).selectedHabit
        val viewModelFactory = NewHabitViewModelFactory(dataSource, selectedHabit)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(NewHabitViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.newHabitViewModel = viewModel
    }


    private fun initObservers() {
        viewModel.backButtonVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) binding.habitBackButton.visibility = View.VISIBLE
                else binding.habitBackButton.visibility = View.GONE
            }
        })

        viewModel.cardViewDescriptionVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutDescription.cardviewHabitDescription.visibility = View.VISIBLE
                    binding.layoutDescription.editHabitDescription.hint = selectedHabit.habitDescription
                    binding.layoutDescription.editHabitDescription.addTextChangedListener { input: Editable? ->
                        input?.let { viewModel.onDescriptionInputChanged(input.toString()) }
                    }
                } else binding.layoutDescription.cardviewHabitDescription.visibility = View.GONE
            }
        })

        viewModel.cardViewRatingVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutRating.cardviewRating.visibility = View.VISIBLE
                    val radioGroup = binding.layoutRating.ratingRadioGroup

                    when (selectedHabit.habitRating) {
                        Rating.POSITIVE.name -> radioGroup.check(R.id.positive_button)
                        Rating.NEUTRAL.name -> radioGroup.check(R.id.neutral_button)
                        Rating.NEGATIVE.name -> radioGroup.check(R.id.negative_button)
                    }

                    radioGroup.setOnCheckedChangeListener { rg: RadioGroup?, id: Int ->
                        rg?.let {
                            when (id) {
                                rg[0].id -> viewModel.onRatingInputChanged(Rating.POSITIVE.name)
                                rg[1].id -> viewModel.onRatingInputChanged(Rating.NEUTRAL.name)
                                rg[2].id -> viewModel.onRatingInputChanged(Rating.NEGATIVE.name)
                            }
                        }
                    }
                } else binding.layoutRating.cardviewRating.visibility = View.GONE
            }
        })

        viewModel.cardViewPriorityVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutPriority.cardviewPriority.visibility = View.VISIBLE
                    val radioGroup = binding.layoutPriority.priorityRadioGroup

                    when (selectedHabit.habitPriority) {
                        Priority.LOW.name -> radioGroup.check(R.id.low_priority_button)
                        Priority.MEDIUM.name -> radioGroup.check(R.id.med_priority_button)
                        Priority.HIGH.name -> radioGroup.check(R.id.high_priority_button)
                    }

                    radioGroup.setOnCheckedChangeListener { rg: RadioGroup?, id: Int ->
                        rg?.let {
                            when (id) {
                                rg[0].id -> viewModel.onPriorityInputChanged(Priority.LOW.name)
                                rg[1].id -> viewModel.onPriorityInputChanged(Priority.MEDIUM.name)
                                rg[2].id -> viewModel.onPriorityInputChanged(Priority.HIGH.name)
                            }
                        }
                    }
                } else binding.layoutPriority.cardviewPriority.visibility = View.GONE
            }
        })

        viewModel.isInputReceived.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    requireView().findNavController().navigate(R.id.action_editHabitFragment_to_dashBoard)
                    Toast.makeText(requireContext(), "Current Habit Changed", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(requireContext(), "Must fill in requirements", Toast.LENGTH_SHORT).show()
            }
        })
    }

}