package com.example.habitstacks.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.habitstacks.R
import com.example.habitstacks.database.TrackerEntryDatabase
import com.example.habitstacks.databinding.NewTrackerQuestionsBinding
import com.example.habitstacks.viewmodel.NewTrackerViewModel
import com.example.habitstacks.viewmodel.NewTrackerViewModelFactory

class NewTrackerFragment : Fragment() {

    private lateinit var binding: NewTrackerQuestionsBinding
    private lateinit var viewModel: NewTrackerViewModel
    private lateinit var associatedHabit: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.new_tracker_questions, container, false)

        initBackend()
        initObservers()

        return binding.root
    }

    private fun initBackend() {
        val dataSource = TrackerEntryDatabase
                .getInstance(requireContext())
                .trackerEntryDao

        associatedHabit = NewTrackerFragmentArgs.fromBundle(requireArguments()).associatedHabit
        val viewModelFactory = NewTrackerViewModelFactory(dataSource, associatedHabit)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(NewTrackerViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.newTrackerViewModel = viewModel
    }


    private fun initObservers() {
        viewModel.backButtonVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) binding.trackBackButton.visibility = View.VISIBLE
                else binding.trackBackButton.visibility = View.GONE
            }
        })

        viewModel.cardViewLocationVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutLocation.cardviewTrackLocation.visibility = View.VISIBLE
                    binding.layoutLocation.editTrackLocation.addTextChangedListener { location: Editable? ->
                        location?.let { viewModel.onLocationInputChanged(location.toString()) }
                    }
                } else binding.layoutLocation.cardviewTrackLocation.visibility = View.GONE
            }
        })

        viewModel.cardViewEmotionVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutEmotion.cardviewTrackEmotion.visibility = View.VISIBLE
                    binding.layoutEmotion.editTrackEmotion.addTextChangedListener { emotion: Editable? ->
                        emotion?.let { viewModel.onEmotionInputChanged(emotion.toString()) }
                    }
                } else binding.layoutEmotion.cardviewTrackEmotion.visibility = View.GONE
            }
        })

        viewModel.cardViewActionVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutAction.cardviewTrackAction.visibility = View.VISIBLE
                    binding.layoutAction.editTrackAction.addTextChangedListener { action: Editable? ->
                        action?.let { viewModel.onActionInputChanged(action.toString()) }
                    }
                } else binding.layoutAction.cardviewTrackAction.visibility = View.GONE
            }
        })

        viewModel.isInputReceived.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) requireView().findNavController().navigate(NewTrackerFragmentDirections
                        .actionNewTackerToTrackerOverview(associatedHabit))
                else Toast.makeText(requireContext(), "Must fill in requirements", Toast.LENGTH_SHORT).show()
            }
        })
    }

}