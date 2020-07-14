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
import com.example.habitstacks.R
import com.example.habitstacks.database.HabitDatabase
import com.example.habitstacks.databinding.NewHabitBinding
import com.example.habitstacks.model.Priority
import com.example.habitstacks.model.Rating
import com.example.habitstacks.viewmodel.NewHabitViewModel
import com.example.habitstacks.viewmodel.NewHabitViewModelFactory

class NewHabitFragment : Fragment() {

    private lateinit var binding: NewHabitBinding
    private lateinit var viewModel: NewHabitViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.new_habit, container, false)

        initBackend()
        initObservers()

        return binding.root
    }

    private fun initBackend() {
        val dataSource = HabitDatabase
                .getInstance(requireContext())
                .habitDao
        val viewModelFactory = NewHabitViewModelFactory(dataSource)
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
                    binding.layoutRating.ratingRadioGroup.setOnCheckedChangeListener { rg: RadioGroup?, id: Int ->
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
                    binding.layoutPriority.priorityRadioGroup.setOnCheckedChangeListener { rg: RadioGroup?, id: Int ->
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
                if (it) requireView().findNavController().navigate(R.id.action_newHabit_to_dashBoard)
                else Toast.makeText(requireContext(), "Must fill in requirements", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
