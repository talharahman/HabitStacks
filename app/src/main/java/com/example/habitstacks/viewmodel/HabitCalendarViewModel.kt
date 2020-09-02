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

class HabitCalendarViewModel(private val dataSource: HabitDao,
                             private val selectedHabit: Habit) : ViewModel() {

    private val database = dataSource
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _habitDescription = MutableLiveData<String>()
    val habitDescription: LiveData<String> get() = _habitDescription

    init {
        _habitDescription.value = selectedHabit.habitDescription
    }

}

@Suppress("UNCHECKED_CAST")
class HabitCalendarViewModelFactory(
        private val dataSource: HabitDao,
        private val selectedHabit: Habit) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitCalendarViewModel::class.java)) {
            return HabitCalendarViewModel(dataSource, selectedHabit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
