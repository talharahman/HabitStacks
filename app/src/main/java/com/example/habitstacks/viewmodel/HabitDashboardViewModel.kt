package com.example.habitstacks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.HabitDao
import com.example.habitstacks.model.Habit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HabitDashboardViewModel(dataSource: HabitDao) : ViewModel() {

    private val dataBase = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _selectedHabit = MutableLiveData<String>()
    val selectedHabit: LiveData<String> get() = _selectedHabit

    private val _onTrackButtonClicked = MutableLiveData<Boolean>()
    val onTrackButtonClicked: LiveData<Boolean> get() = _onTrackButtonClicked

    private val _onGoalButtonClicked = MutableLiveData<Boolean>()
    val onGoalButtonClicked: LiveData<Boolean> get() = _onGoalButtonClicked

    val habits = dataBase.getAllHabits()

    fun navigateToTrackerEntries(item: Habit) {
        _onTrackButtonClicked.value = true
        _selectedHabit.value = item.habitDescription
    }

    fun navigateToHabitGoals(item: Habit) {
        _onGoalButtonClicked.value = true
        _selectedHabit.value = item.habitDescription
    }

    fun selectedHabitComplete() {
        _selectedHabit.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

@Suppress("UNCHECKED_CAST")
class DashBoardViewModelFactory(
        private val dataSource: HabitDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitDashboardViewModel::class.java)) {
            return HabitDashboardViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}