package com.example.habitstacks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstacks.database.HabitDao

class TrackQuestionViewModel(dataSource: HabitDao) : ViewModel()  {


}


@Suppress("UNCHECKED_CAST")
class TrackQuestionViewModelFactory(
        private val dataSource: HabitDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackQuestionViewModel::class.java)) {
            return TrackQuestionViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
