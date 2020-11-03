package com.example.habitstacks.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.habitstacks.database.HabitDao
import com.example.habitstacks.database.TrackerEntryDao
import com.example.habitstacks.database.TrackerEntryDatabase
import com.example.habitstacks.model.Habit
import com.example.habitstacks.model.HabitTrackerEntry
import kotlinx.coroutines.*

class HabitCalendarViewModel(private val dataSource: HabitDao,
                             private val selectedHabit: Habit,
                             application: Application) : AndroidViewModel(application) {

    private val database = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _habitDescription = MutableLiveData<String>()
    val habitDescription: LiveData<String> get() = _habitDescription

    private val _trackerEntries = MutableLiveData<List<HabitTrackerEntry>>()
    val trackerEntries: LiveData<List<HabitTrackerEntry>> get() = _trackerEntries

    init {
        _habitDescription.value = selectedHabit.habitDescription
    }

    private fun getTrackerEntries() {
        uiScope.launch {
            val trackerDatabase = TrackerEntryDatabase
                    .getInstance(getApplication()).trackerEntryDao
            _trackerEntries.value = fetchEntries(trackerDatabase)
        }
    }

    private suspend fun fetchEntries(trackerDao: TrackerEntryDao): List<HabitTrackerEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext trackerDao.getEntriesForHabit(habitDescription.value!!)
        }
    }

    fun onDateClicked() {

    }

}

@Suppress("UNCHECKED_CAST")
class HabitCalendarViewModelFactory(
        private val dataSource: HabitDao,
        private val selectedHabit: Habit,
        private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitCalendarViewModel::class.java)) {
            return HabitCalendarViewModel(dataSource, selectedHabit, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
