package com.example.habitstacks.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.HabitDao
import com.example.habitstacks.model.Habit
import kotlinx.coroutines.*

class NewHabitViewModel(dataSource: HabitDao) : ViewModel() {

    private val dataBase = dataSource

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _inputDescription = MutableLiveData<String>()
    val inputDescription: LiveData<String> get() = _inputDescription

    fun onTextChanged(input: String) { _inputDescription.value = input }

    fun newHabitSubmit() {
        uiScope.launch {
            val newHabit = Habit(inputDescription.value!!.toString())
            insert(newHabit)
        }
    }

    private suspend fun insert(habit: Habit) {
        withContext(Dispatchers.IO) { dataBase.insert(habit) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


@Suppress("UNCHECKED_CAST")
class NewHabitViewModelFactory(
        private val dataSource: HabitDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewHabitViewModel::class.java)) {
            return NewHabitViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}