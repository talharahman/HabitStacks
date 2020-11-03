package com.example.habitstacks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.TrackerEntryDao
import com.example.habitstacks.model.HabitTrackerEntry
import kotlinx.coroutines.*

class TrackerOverviewViewModel(dataSource: TrackerEntryDao, habitDescription: String) : ViewModel(){

    private val dataBase = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _selectedHabit = MutableLiveData<String>()
    val selectedHabit: LiveData<String> get() = _selectedHabit

    private var _trackerEntries = MutableLiveData<List<HabitTrackerEntry>>()
    val trackerEntries: LiveData<List<HabitTrackerEntry>> get() = _trackerEntries

    private var _trackersAvailable = MutableLiveData<Boolean>()
    val trackersAvailable: LiveData<Boolean> get() = _trackersAvailable

    init {
        _selectedHabit.value = habitDescription
        getTrackerEntriesFromDatabase()
    }

    private fun getTrackerEntriesFromDatabase() {
        uiScope.launch {
            _trackerEntries.value = getEntriesForHabit()
            _trackersAvailable.value = !_trackerEntries.value.isNullOrEmpty()
        }
    }

    private suspend fun getEntriesForHabit(): List<HabitTrackerEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext dataBase.getEntriesForHabit(selectedHabit.value!!)
        }
    }

    fun deleteSelectedEntry(trackerEntry: HabitTrackerEntry) {
        uiScope.launch {
            deleteEntryFromDatabase(trackerEntry)
        }
    }

    private suspend fun deleteEntryFromDatabase(trackerEntry: HabitTrackerEntry) {
        withContext(Dispatchers.IO) {
            dataBase.delete(trackerEntry.trackId)
        }
        getTrackerEntriesFromDatabase()
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
