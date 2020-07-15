package com.example.habitstacks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.TrackerEntryDao
import com.example.habitstacks.model.HabitTrackerEntry
import kotlinx.coroutines.*

class TrackerOverviewViewModel(dataSource: TrackerEntryDao, habitDescription: String) : ViewModel() {

    private val dataBase = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _selectedHabit = MutableLiveData<String>()
    val selectedHabit: LiveData<String> get() = _selectedHabit

    private var _trackerEntries = MutableLiveData<List<HabitTrackerEntry>>()
    val trackerEntries: LiveData<List<HabitTrackerEntry>> get() = _trackerEntries

    init {
        _selectedHabit.value = habitDescription
        getTrackerEntriesFromDatabase()
    }

    private fun getTrackerEntriesFromDatabase() {
        uiScope.launch {
            _trackerEntries.value = getEntriesForHabit()
        }
    }

    private suspend fun getEntriesForHabit(): List<HabitTrackerEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext dataBase.getEntriesForHabit(selectedHabit.value!!)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}

@Suppress("UNCHECKED_CAST")
class TrackerOverviewViewModelFactory(
        private val dataSource: TrackerEntryDao,
        private val selectedHabit: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackerOverviewViewModel::class.java)) {
            return TrackerOverviewViewModel(dataSource, selectedHabit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}