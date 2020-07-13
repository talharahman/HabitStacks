package com.example.habitstacks.viewmodel

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

    private val inputDescription = MutableLiveData<String>()
    private val inputRating = MutableLiveData<String>()
    private val inputPriority = MutableLiveData<String>()
    val isInputReceived = MutableLiveData<Boolean?>()

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
        isInputReceived.value = null
    }


    fun onDescriptionInputChanged(input: String) {
        inputDescription.value = input
    }

    fun onRatingInputChanged(input: String) {
        inputRating.value = input
    }

    fun onPriorityInputChanged(input: String) {
        inputPriority.value = input
    }


    fun navigateToNextView() {
        when (habitCardPosition.name) {
            "DESCRIPTION" -> {
                if (inputDescription.value != null) {
                    _cardViewDescriptionVisible.value = false
                    _cardViewRatingVisible.value = true
                    _backButtonVisible.value = true
                    habitCardPosition = NewHabitCards.RATING
                } else isInputReceived.value = false
            }
            "RATING" -> {
                if (inputRating.value != null) {
                    _cardViewRatingVisible.value = false
                    _cardViewPriorityVisible.value = true
                    habitCardPosition = NewHabitCards.PRIORITY
                } else isInputReceived.value = false
            }
            "PRIORITY" -> {
                if (inputPriority.value != null) {
                    newHabitSubmit()
                    isInputReceived.value = true
                } else isInputReceived.value = false
            }
        }
    }

    fun navigateToPreviousView() {
        when (habitCardPosition.name) {
            "DESCRIPTION" -> {
                _backButtonVisible.value = false
            }
            "RATING" -> {
                _cardViewRatingVisible.value = false
                _cardViewDescriptionVisible.value = true
                habitCardPosition = NewHabitCards.DESCRIPTION
            }
            "PRIORITY" -> {
                _cardViewPriorityVisible.value = false
                _cardViewRatingVisible.value = true
                habitCardPosition = NewHabitCards.RATING
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

enum class NewHabitCards { DESCRIPTION, RATING, PRIORITY }