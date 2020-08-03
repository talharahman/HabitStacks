package com.example.habitstacks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDeepLinkBuilder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.habitstacks.view.HabitDashboard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule

@RunWith(AndroidJUnit4::class)
class DashboardTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private fun launchFragment(destinationId: Int,
                               argBundle: Bundle? = null) {
        val launchFragmentIntent = buildLaunchFragmentIntent(destinationId, argBundle)
        activityRule.launchActivity(launchFragmentIntent)
    }

    private fun buildLaunchFragmentIntent(destinationId: Int, argBundle: Bundle?): Intent =
            NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().targetContext)
                    .setGraph(R.navigation.navigation)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(destinationId)
                    .setArguments(argBundle)
                    .createTaskStackBuilder().intents[0]


    @Test
    fun performNewHabitOnClick() {
        launchFragment(R.id.dashBoard)
        onView(withId(R.id.new_habit_button)).perform(click())
    }


}

