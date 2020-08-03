package com.example.habitstacks

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.habitstacks.view.NewHabitFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddNewHabitTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var habitDescription: String

    @Before
    fun validString() {
        habitDescription = "Sleep for 6 hours min daily"
    }

    @Test
    fun inputHabitEditText() {
        launchFragmentInContainer<NewHabitFragment>()
        onView(withId(R.id.edit_habit_description))
                .perform(typeText(habitDescription))
        onView(withId(R.id.habit_submit_button))
                .perform(click())
    }
}