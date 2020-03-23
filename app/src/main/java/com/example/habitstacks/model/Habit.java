package com.example.habitstacks.model;

public class Habit {

    private String habitDescription;

    public Habit(String habitDescription) {
        this.habitDescription = habitDescription;
    }

    public String getHabitDescription() {
        return habitDescription;
    }

    public void setHabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }
}
