package com.example.habitstacks.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habitstacks.model.Habit

@Dao
interface HabitDao {

    @Insert
    fun insert(habit: Habit)

//    @Update
//    fun update(habit: Habit)

//    @Query("SELECT * from habits_table WHERE habitId = :key")
//    fun get(key: Long): Habit

//    @Query("DELETE from habits_table")
//    fun clear()

    @Query("SELECT * from habits_table ORDER BY habitId DESC")
    fun getAllHabits(): LiveData<List<Habit>>

//    @Query("SELECT * from habits_table WHERE habitId = :key")
//    fun getHabitWithId(key: Long): LiveData<Habit>
}