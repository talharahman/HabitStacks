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
import androidx.navigation.fragment.findNavController
import com.example.habitstacks.databinding.NewEditTrackerQuestionsBinding
import com.example.habitstacks.R
import com.example.habitstacks.database.TrackerEntryDatabase
import com.example.habitstacks.model.HabitTrackerEntry
import com.example.habitstacks.viewmodel.NewEditTrackerViewModel
import com.example.habitstacks.viewmodel.NewEditTrackerViewModelFactory

class EditTrackerFragment : Fragment() {

    private lateinit var binding: NewEditTrackerQuestionsBinding
    private lateinit var viewModelEdit: NewEditTrackerViewModel
    private lateinit var selectedTrack: HabitTrackerEntry

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.new_edit_tracker_questions, container, false)

        initBackend()
        initObservers()

        return binding.root
    }

    private fun initBackend() {
        val dataSource = TrackerEntryDatabase
                .getInstance(requireContext())
                .trackerEntryDao
        selectedTrack = EditTrackerFragmentArgs.fromBundle(requireArguments()).selectedTrack

        val viewModelFactory = NewEditTrackerViewModelFactory(dataSource, selectedTrack.associatedHabit, selectedTrack)
        viewModelEdit = ViewModelProvider(this, viewModelFactory)
                .get(NewEditTrackerViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.newTrackerViewModel = viewModelEdit
        viewModelEdit.setValuesForEdit(selectedTrack)
    }

    private fun initObservers() {
        viewModelEdit.backButtonVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) binding.trackBackButton.visibility = View.VISIBLE
                else binding.trackBackButton.visibility = View.GONE
            }
        })

        viewModelEdit.cardViewLocationVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutLocation.cardviewTrackLocation.visibility = View.VISIBLE
                    binding.layoutLocation.editTrackLocation.setText(selectedTrack.trackLocation)
                    binding.layoutLocation.editTrackLocation.addTextChangedListener { location: Editable? ->
                        location?.let { viewModelEdit.onLocationInputChanged(location.toString()) }
                    }
                } else binding.layoutLocation.cardviewTrackLocation.visibility = View.GONE
            }
        })

        viewModelEdit.cardViewEmotionVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutEmotion.cardviewTrackEmotion.visibility = View.VISIBLE
                    binding.layoutEmotion.editTrackEmotion.setText(selectedTrack.trackEmotion)
                    binding.layoutEmotion.editTrackEmotion.addTextChangedListener { emotion: Editable? ->
                        emotion?.let { viewModelEdit.onEmotionInputChanged(emotion.toString()) }
                    }
                } else binding.layoutEmotion.cardviewTrackEmotion.visibility = View.GONE
            }
        })

        viewModelEdit.cardViewActionVisible.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    binding.layoutAction.cardviewTrackAction.visibility = View.VISIBLE
                    binding.layoutAction.editTrackAction.setText(selectedTrack.trackAction)
                    binding.layoutAction.editTrackAction.addTextChangedListener { action: Editable? ->
                        action?.let { viewModelEdit.onActionInputChanged(action.toString()) }
                    }
                } else binding.layoutAction.cardviewTrackAction.visibility = View.GONE
            }
        })

        viewModelEdit.isInputReceived.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    requireView().findNavController().navigate(EditTrackerFragmentDirections
                            .actionEditTrackerFragmentToTrackQuestionsOverview(selectedTrack.associatedHabit))
                    Toast.makeText(requireContext(), "Current Habit Updated", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(requireContext(), "Must fill in requirements", Toast.LENGTH_SHORT).show()
            }
        })
    }
}