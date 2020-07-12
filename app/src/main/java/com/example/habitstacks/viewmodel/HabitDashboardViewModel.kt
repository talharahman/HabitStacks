package com.example.habitstacks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.HabitDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HabitDashboardViewModel(dataSource: HabitDao) : ViewModel() {

    private val dataBase = dataSource

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _showSelectedHabit = MutableLiveData<Long>()
    val showSelectedHabit: LiveData<Long> get() = _showSelectedHabit

    val habits = dataBase.getAllHabits()

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