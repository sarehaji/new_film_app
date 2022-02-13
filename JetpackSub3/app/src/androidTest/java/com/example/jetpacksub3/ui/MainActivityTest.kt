package com.example.jetpacksub3.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.jetpacksub3.R
import com.example.jetpacksub3.utils.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest{
    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun checkMovies() {
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .check(matches(isDisplayed()))
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }
    @Test
    fun checkTvShow(){
        onView(withText("TV SHOWS")).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .check(matches(isDisplayed()))
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun checkDetails() {
        onView(withText("MOVIES")).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    click()
                ))
        onView(withId(R.id.detail_title)).check(
            matches(isDisplayed()))
        onView(withId(R.id.detail_title)).check(
            matches(
                withText("Luca")))
        onView(withId(R.id.film_image)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_release_image)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.film_image)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_release)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_popularity)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_genre)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_vote_average)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_runtime)).check(
            matches(
                isDisplayed()))
        onView(isRoot()).perform(pressBack())
    }
    @Test
    fun checkDetailTv(){
        onView(withText("TV SHOWS")).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    click()
                ))
        onView(withId(R.id.detail_title)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_title)).check(
            matches(
                withText("Loki")))
        onView(withId(R.id.film_image)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_release_image)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.film_image)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_release)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_popularity)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_genre)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_vote_average)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.detail_runtime)).check(
            matches(
                isDisplayed()))
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun checkFavorites() {
        onView(withId(R.id.favorite)).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
        onView(withText("Luca")).check(doesNotExist())
        onView(isRoot()).perform(pressBack())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    click()
                ))
        onView(withId(R.id.fav_button)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.favorite)).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .check(matches(isDisplayed()))
        onView(withText("Luca")).check(matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    click()
                ))
        onView(withId(R.id.fav_button)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite)).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .check(matches(isDisplayed()))
        onView(withText("Luca")).check(doesNotExist())
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun checkFavoritesTV(){
        onView(withId(R.id.favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withText("Loki")).check(doesNotExist())
        onView(isRoot()).perform(pressBack())
        onView(withText("TV SHOWS")).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    click()
                ))
        onView(withId(R.id.fav_button)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite)).check(
            matches(
                isDisplayed()))
        onView(withId(R.id.favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .check(matches(isDisplayed()))
        onView(withText("Loki")).check(matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    click()
                ))
        onView(withId(R.id.fav_button)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(
            Matchers.allOf(
                withId(R.id.recyclerView),
                withEffectiveVisibility(Visibility.VISIBLE),
                isCompletelyDisplayed()
            )
        )
            .check(matches(isDisplayed()))
        onView(withText("Loki")).check(doesNotExist())
        onView(isRoot()).perform(pressBack())
    }
}