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
    private var habitCardPosition: NewHabitCards
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _inputDescription = MutableLiveData<String>()
    val inputDescription: LiveData<String> get() = _inputDescription

    private val _backButtonVisible = MutableLiveData<Boolean>()
    val backButtonVisible: LiveData<Boolean> get() = _backButtonVisible

    private val _cardViewDescriptionVisible = MutableLiveData<Boolean>()
    val cardViewDescriptionVisible: LiveData<Boolean> get() = _cardViewDescriptionVisible

    private val _cardViewPosNegVisible = MutableLiveData<Boolean>()
    val cardViewPosNegVisible: LiveData<Boolean> get() = _cardViewPosNegVisible

    private val _cardViewPriorityVisible = MutableLiveData<Boolean>()
    val cardViewPriorityVisible: LiveData<Boolean> get() = _cardViewPriorityVisible

    init {
        _backButtonVisible.value = false
        _cardViewDescriptionVisible.value = true
        _cardViewPosNegVisible.value = false
        _cardViewPriorityVisible.value = false
        habitCardPosition = NewHabitCards.DESCRIPTION
    }

    fun onTextChanged(input: String) {
        _inputDescription.value = input
    }

    fun navigateToNextView() {
        when (habitCardPosition) {
            NewHabitCards.DESCRIPTION -> {
                _cardViewDescriptionVisible.value = false
                _cardViewPosNegVisible.value = true
                _backButtonVisible.value = true
                habitCardPosition = NewHabitCards.QUESTION
            }
            NewHabitCards.QUESTION -> {
                _cardViewPosNegVisible.value = false
                habitCardPosition = NewHabitCards.PRIORITY
            }
            NewHabitCards.PRIORITY -> { newHabitSubmit() }
        }
    }

    fun navigateToPreviousView() {
        when (habitCardPosition) {
            NewHabitCards.DESCRIPTION -> {
                _backButtonVisible.value = false
            }
            NewHabitCards.QUESTION -> {
                _cardViewPosNegVisible.value = false
                _cardViewDescriptionVisible.value = true
                habitCardPosition = NewHabitCards.DESCRIPTION
            }
            NewHabitCards.PRIORITY -> {
                _cardViewPosNegVisible.value = true
                habitCardPosition = NewHabitCards.QUESTION
            }
        }
    }

    private fun newHabitSubmit() {
        uiScope.launch {
        //    val newHabit = Habit(inputDescription.value!!.toString())
        //    insert(newHabit)
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

enum class NewHabitCards { DESCRIPTION, QUESTION, PRIORITY }