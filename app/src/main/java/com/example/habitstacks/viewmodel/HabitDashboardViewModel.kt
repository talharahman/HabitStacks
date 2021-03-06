package com.example.habitstacks.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.habitstacks.database.HabitDao
import com.example.habitstacks.database.TrackerEntryDao
import com.example.habitstacks.database.TrackerEntryDatabase
import com.example.habitstacks.model.Habit
import kotlinx.coroutines.*

class HabitDashboardViewModel(private val habitDatabase: HabitDao, application: Application)
    : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _selectedHabit = MutableLiveData<String>()
    val selectedHabit: LiveData<String> get() = _selectedHabit

    private var _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> get() = _habits

    private var _habitsAvailable = MutableLiveData<Boolean>()
    val habitsAvailable: LiveData<Boolean> get() = _habitsAvailable

    init {
        getHabitsFromDatabase()
    }

    private fun getHabitsFromDatabase() {
        uiScope.launch {
            _habits.value = getAllHabits()
            _habitsAvailable.value = !_habits.value.isNullOrEmpty()
        }
    }

    private suspend fun getAllHabits(): List<Habit> {
        return withContext(Dispatchers.IO) {
            return@withContext habitDatabase.getAllHabits()
        }
    }

    fun deleteSelectedHabit(habit: Habit) {
        uiScope.launch {
            val trackerDatabase = TrackerEntryDatabase
                    .getInstance(getApplication()).trackerEntryDao
            deleteHabitFromSources(habit, trackerDatabase)
        }
    }

    private suspend fun deleteHabitFromSources(habit: Habit, trackerDatabase: TrackerEntryDao) {
        withContext(Dispatchers.IO) {
            habitDatabase.delete(habit.habitId)
            trackerDatabase.deleteLinkedEntries(habit.habitDescription)
        }
        getHabitsFromDatabase()
    }

    fun updateFrequencyCount(habit: Habit, increment: Boolean) {
        uiScope.launch {
            updateFrequencyFromSource(habit, increment)
        }
    }

    private suspend fun updateFrequencyFromSource(item: Habit, increment: Boolean) {
        withContext(Dispatchers.IO) {
            if (!increment && item.frequencyCount > 0) item.frequencyCount--
            else if (increment) item.frequencyCount++
            habitDatabase.update(item)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

@Suppress("UNCHECKED_CAST")
class DashBoardViewModelFactory(
        private val dataSource: HabitDao,
        private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitDashboardViewModel::class.java)) {
            return HabitDashboardViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}