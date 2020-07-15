package com.example.habitstacks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.TrackerEntryDao
import com.example.habitstacks.model.HabitTrackerEntry
import kotlinx.coroutines.*

class NewTrackerViewModel(private val dataSource: TrackerEntryDao,
                          private val habitDescription: String) : ViewModel() {

    private var trackCardPosition: TrackQuestionCards
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val inputLocation = MutableLiveData<String>()
    private val inputEmotion = MutableLiveData<String>()
    private val inputAction = MutableLiveData<String>()

    val isInputReceived = MutableLiveData<Boolean?>()

    private val _backButtonVisible = MutableLiveData<Boolean>()
    val backButtonVisible: LiveData<Boolean> get() = _backButtonVisible

    private val _cardViewLocationVisible = MutableLiveData<Boolean>()
    val cardViewLocationVisible: LiveData<Boolean> get() = _cardViewLocationVisible

    private val _cardViewEmotionVisible = MutableLiveData<Boolean>()
    val cardViewEmotionVisible: LiveData<Boolean> get() = _cardViewEmotionVisible

    private val _cardViewActionVisible = MutableLiveData<Boolean>()
    val cardViewActionVisible: LiveData<Boolean> get() = _cardViewActionVisible


    init {
        _backButtonVisible.value = false
        _cardViewLocationVisible.value = true
        _cardViewEmotionVisible.value = false
        _cardViewActionVisible.value = false
        trackCardPosition = TrackQuestionCards.LOCATION
        isInputReceived.value = null

    }

    fun onLocationInputChanged(input: String) {
        inputLocation.value = input
    }

    fun onEmotionInputChanged(input: String) {
        inputEmotion.value = input
    }

    fun onActionInputChanged(input: String) {
        inputAction.value = input
    }

    fun navigateToNextView() {
        when (trackCardPosition.name) {
            "LOCATION" -> {
                if (inputLocation.value != null) {
                    _cardViewLocationVisible.value = false
                    _cardViewEmotionVisible.value = true
                    _backButtonVisible.value = true
                    trackCardPosition = TrackQuestionCards.EMOTION
                } else isInputReceived.value = false
            }
            "EMOTION" -> {
                if (inputEmotion.value != null) {
                    _cardViewEmotionVisible.value = false
                    _cardViewActionVisible.value = true
                    trackCardPosition = TrackQuestionCards.ACTION
                } else isInputReceived.value = false
            }
            "ACTION" -> {
                if (inputAction.value != null) {
                    newTrackEntrySubmit()
                    isInputReceived.value = true
                } else isInputReceived.value = false
            }
        }
    }

    fun navigateToPreviousView() {
        when (trackCardPosition.name) {
            "LOCATION" -> {
                _backButtonVisible.value = false
            }
            "EMOTION" -> {
                _cardViewEmotionVisible.value = false
                _cardViewLocationVisible.value = true
                trackCardPosition = TrackQuestionCards.LOCATION
            }
            "ACTION" -> {
                _cardViewActionVisible.value = false
                _cardViewEmotionVisible.value = true
                trackCardPosition = TrackQuestionCards.EMOTION
            }
        }
    }

    private fun newTrackEntrySubmit() {
        uiScope.launch {
            val newTrackerEntry = HabitTrackerEntry(
                    habitDescription,
                    System.currentTimeMillis(),
                    inputLocation.value!!,
                    inputEmotion.value!!,
                    inputAction.value!!)
            insert(newTrackerEntry)
        }
    }

    private suspend fun insert(trackerEntry: HabitTrackerEntry) {
        withContext(Dispatchers.IO) { dataSource.insert(trackerEntry) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}


@Suppress("UNCHECKED_CAST")
class NewTrackerViewModelFactory(
        private val dataSource: TrackerEntryDao,
        private val selectedHabit: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewTrackerViewModel::class.java)) {
            return NewTrackerViewModel(dataSource, selectedHabit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class TrackQuestionCards { LOCATION, EMOTION, ACTION }