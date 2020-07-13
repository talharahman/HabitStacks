package com.example.habitstacks.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.HabitDao
import com.example.habitstacks.model.Habit
import com.example.habitstacks.model.Priority
import com.example.habitstacks.model.Rating
import kotlinx.coroutines.*

class NewHabitViewModel(dataSource: HabitDao) : ViewModel() {

    private val dataBase = dataSource
    private var habitCardPosition: NewHabitCards
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _inputDescription = MutableLiveData<String>()
    val inputDescription: LiveData<String> get() = _inputDescription

    private val _inputRating = MutableLiveData<String>()
    val inputRating: LiveData<String> get() = _inputRating

    private val _inputPriority = MutableLiveData<String>()
    val inputPriority: LiveData<String> get() = _inputPriority


    private val _backButtonVisible = MutableLiveData<Boolean>()
    val backButtonVisible: LiveData<Boolean> get() = _backButtonVisible

    private val _cardViewDescriptionVisible = MutableLiveData<Boolean>()
    val cardViewDescriptionVisible: LiveData<Boolean> get() = _cardViewDescriptionVisible

    private val _cardViewRatingVisible = MutableLiveData<Boolean>()
    val cardViewRatingVisible: LiveData<Boolean> get() = _cardViewRatingVisible

    private val _cardViewPriorityVisible = MutableLiveData<Boolean>()
    val cardViewPriorityVisible: LiveData<Boolean> get() = _cardViewPriorityVisible


    init {
        _backButtonVisible.value = false
        _cardViewDescriptionVisible.value = true
        _cardViewRatingVisible.value = false
        _cardViewPriorityVisible.value = false
        habitCardPosition = NewHabitCards.DESCRIPTION
    }


    fun onDescriptionChanged(input: String) {
        _inputDescription.value = input
        Log.d("NEWHABITVM", inputDescription.value.toString())
    }

    fun onRatingChanged(input: String) {
        _inputRating.value = input
        Log.d("NEWHABITVM", inputRating.value.toString())
    }

    fun onPriorityChanged(input: String) {
        _inputPriority.value = input
        Log.d("NEWHABITVM", inputPriority.value.toString())
    }


    fun navigateToNextView() {
        when (habitCardPosition.name) {
            "DESCRIPTION" -> {
                _cardViewDescriptionVisible.value = false
                _cardViewRatingVisible.value = true
                _backButtonVisible.value = true
                habitCardPosition = NewHabitCards.QUESTION
            }
            "QUESTION" -> {
                _cardViewRatingVisible.value = false
                _cardViewPriorityVisible.value = true
                habitCardPosition = NewHabitCards.PRIORITY
            }
            "PRIORITY" -> {
                 newHabitSubmit()
            }
        }
    }

    fun navigateToPreviousView() {
        when (habitCardPosition.name) {
            "DESCRIPTION" -> {
                _backButtonVisible.value = false
            }
            "QUESTION" -> {
                _cardViewRatingVisible.value = false
                _cardViewDescriptionVisible.value = true
                habitCardPosition = NewHabitCards.DESCRIPTION
            }
            "PRIORITY" -> {
                _cardViewPriorityVisible.value = false
                _cardViewRatingVisible.value = true
                habitCardPosition = NewHabitCards.QUESTION
            }
        }
    }


    private fun newHabitSubmit() {
        uiScope.launch {
            val newHabit = Habit(
                    inputDescription.value!!,
                    inputRating.value!!,
                    inputPriority.value!!)
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

enum class NewHabitCards { DESCRIPTION, QUESTION, PRIORITY }