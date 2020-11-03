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
import com.example.habitstacks.databinding.NewEditHabitBinding
import com.example.habitstacks.model.Rating
import com.example.habitstacks.model.Duration
import com.example.habitstacks.viewmodel.NewEditHabitViewModel
import com.example.habitstacks.viewmodel.NewHabitViewModelFactory

class NewHabitFragment : Fragment() {

    private lateinit var binding: NewEditHabitBinding
    private lateinit var viewModelEdit: NewEditHabitViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.new_edit_habit, container, false)

        initBackend()
        initObservers()

        return binding.root
    }

    private fun initBackend() {
        val dataSource = HabitDatabase
                .getInstance(requireContext())
                .habitDao
        val viewModelFactory = NewHabitViewModelFactory(dataSource, null)
        viewModelEdit = ViewModelProvider(this, viewModelFactory)
                .get(NewEditHabitViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.newHabitViewModel = viewModelEdit
    }


    private fun initObservers() {
        viewModelEdit.backButtonVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) binding.habitBackButton.visibility = View.VISIBLE
                else binding.habitBackButton.visibility = View.GONE
            }
        })

        viewModelEdit.cardViewDescriptionVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutDescription.cardviewHabitDescription.visibility = View.VISIBLE
                    binding.layoutDescription.editHabitDescription.addTextChangedListener { input: Editable? ->
                        input?.let { viewModelEdit.onDescriptionInputChanged(input.toString()) }
                    }
                } else binding.layoutDescription.cardviewHabitDescription.visibility = View.GONE
            }
        })

        viewModelEdit.cardViewDurationVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutDuration.cardviewDuration.visibility = View.VISIBLE
                    binding.layoutDuration.durationRadioGroup.setOnCheckedChangeListener { rg: RadioGroup?, id: Int ->
                        rg?.let {
                            when (id) {
                                rg[0].id -> viewModelEdit.onDurationInputChanged(Duration.DAILY.name)
                                rg[1].id -> viewModelEdit.onDurationInputChanged(Duration.WEEKLY.name)
                                rg[2].id -> viewModelEdit.onDurationInputChanged(Duration.MONTHLY.name)
                            }
                        }
                    }
                    viewModelEdit.inputFrequency.observe(viewLifecycleOwner, Observer { count ->
                        binding.layoutDuration.counterText.text = "$count times"
                    })
                    binding.layoutDuration.countDownButton.setOnClickListener {
                        viewModelEdit.countDown()
                    }
                    binding.layoutDuration.countUpButton.setOnClickListener {
                        viewModelEdit.countUp()
                    }
                } else binding.layoutDuration.cardviewDuration.visibility = View.GONE
            }
        })

        viewModelEdit.cardViewRatingVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutRating.cardviewPriority.visibility = View.VISIBLE
                    binding.layoutRating.priorityRadioGroup.setOnCheckedChangeListener { rg: RadioGroup?, id: Int ->
                        rg?.let {
                            when (id) {
                                rg[0].id -> viewModelEdit.onRatingInputChanged(Rating.POSITIVE.name)
                                rg[1].id -> viewModelEdit.onRatingInputChanged(Rating.NEUTRAL.name)
                                rg[2].id -> viewModelEdit.onRatingInputChanged(Rating.NEGATIVE.name)
                            }
                        }
                    }
                } else binding.layoutRating.cardviewPriority.visibility = View.GONE
            }
        })

        viewModelEdit.allInputReceived.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    requireView().findNavController().navigate(R.id.action_newHabit_to_dashBoard)
                    Toast.makeText(requireContext(), "New Habit Added", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(requireContext(), "Must fill in requirements", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
