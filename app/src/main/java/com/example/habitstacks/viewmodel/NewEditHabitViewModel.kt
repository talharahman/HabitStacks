package com.example.habitstacks.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.HabitDao
import com.example.habitstacks.model.Habit
import kotlinx.coroutines.*

class NewEditHabitViewModel(private val dataSource: HabitDao,
                            private val selectedHabit: Habit?) : ViewModel() {

    private var newHabitCardPosition: NewEditHabitCards
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val inputDescription = MutableLiveData<String>()
    private val inputDuration = MutableLiveData<String>()
    private val inputPriority = MutableLiveData<String>()
    val isInputReceived = MutableLiveData<Boolean?>()
    val inputFrequency = MutableLiveData<Int>()

    private val _backButtonVisible = MutableLiveData<Boolean>()
    val backButtonVisible: LiveData<Boolean> get() = _backButtonVisible

    private val _cardViewDescriptionVisible = MutableLiveData<Boolean>()
    val cardViewDescriptionVisible: LiveData<Boolean> get() = _cardViewDescriptionVisible

    private val _cardViewDurationVisible = MutableLiveData<Boolean>()
    val cardViewDurationVisible: LiveData<Boolean> get() = _cardViewDurationVisible

    private val _cardViewPriorityVisible = MutableLiveData<Boolean>()
    val cardViewPriorityVisible: LiveData<Boolean> get() = _cardViewPriorityVisible


    init {
        _backButtonVisible.value = false
        _cardViewDescriptionVisible.value = true
        _cardViewDurationVisible.value = false
        _cardViewPriorityVisible.value = false
        newHabitCardPosition = NewEditHabitCards.DESCRIPTION
        isInputReceived.value = null
        inputFrequency.value = 1
    }


    fun onDescriptionInputChanged(input: String) {
        inputDescription.value = input
    }

    fun onDurationInputChanged(input: String) {
        inputDuration.value = input
    }


    fun countDown() {
        if (inputFrequency.value!! < 0) {
            inputFrequency.value = inputFrequency.value?.minus(1)
        }
    }

    fun countUp() {
        inputFrequency.value = inputFrequency.value?.plus(1)
    }


    fun onPriorityInputChanged(input: String) {
        inputPriority.value = input
    }


    fun navigateToNextView() {
        when (newHabitCardPosition.name) {
            "DESCRIPTION" -> {
                if (inputDescription.value != null) {
                    _cardViewDescriptionVisible.value = false
                    _cardViewDurationVisible.value = true
                    _backButtonVisible.value = true
                    newHabitCardPosition = NewEditHabitCards.DURATION
                } else isInputReceived.value = false
            }
            "DURATION" -> {
                if (inputDuration.value != null) {
                    _cardViewDurationVisible.value = false
                    _cardViewPriorityVisible.value = true
                    newHabitCardPosition = NewEditHabitCards.PRIORITY
                } else isInputReceived.value = false
            }
            "PRIORITY" -> {
                if (inputPriority.value != null) {
                    if (selectedHabit == null) newHabitSubmit()
                    else editHabitSubmit()

                    isInputReceived.value = true
                } else isInputReceived.value = false
            }
        }
    }


    fun navigateToPreviousView() {
        when (newHabitCardPosition.name) {
            "DESCRIPTION" -> {
                _backButtonVisible.value = false
            }
            "DURATION" -> {
                _cardViewDurationVisible.value = false
                _cardViewDescriptionVisible.value = true
                newHabitCardPosition = NewEditHabitCards.DESCRIPTION
            }
            "PRIORITY" -> {
                _cardViewPriorityVisible.value = false
                _cardViewDurationVisible.value = true
                newHabitCardPosition = NewEditHabitCards.DURATION
            }
        }
    }


    private fun newHabitSubmit() {
        uiScope.launch {
            val newHabit = Habit(
                    inputDescription.value!!,
                    inputDuration.value!!,
                    inputFrequency.value!!,
                    inputPriority.value!!)
            insert(newHabit)
        }
    }

    private suspend fun insert(habit: Habit) {
        withContext(Dispatchers.IO) { dataSource.insert(habit) }
    }

    private fun editHabitSubmit() {
        uiScope.launch {
            selectedHabit?.let {
                selectedHabit.habitDescription = inputDescription.value!!
                selectedHabit.habitDuration = inputDuration.value!!
                selectedHabit.durationFrequency = inputFrequency.value!!
                selectedHabit.habitPriority = inputPriority.value!!
                update(selectedHabit)
            }
        }
    }

    private suspend fun update(habit: Habit) {
        withContext(Dispatchers.IO) { dataSource.update(habit) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


@Suppress("UNCHECKED_CAST")
class NewHabitViewModelFactory(private val dataSource: HabitDao,
                               private val associatedHabit: Habit?) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewEditHabitViewModel::class.java)) {
            return NewEditHabitViewModel(dataSource, associatedHabit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class NewEditHabitCards { DESCRIPTION, DURATION, PRIORITY }