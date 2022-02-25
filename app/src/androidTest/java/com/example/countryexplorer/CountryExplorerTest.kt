package com.example.countryexplorer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountriesExplorerTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Rule
    @JvmField
    val wireMockRule = WireMockRule(8989)

    private val wireMockUtils = WireMockUtils(wireMockRule)

    @Before
    fun setup() {
        wireMockUtils.setup()
    }

    @Test
    fun countriesListIsShownOnRefresh() {
        onView(withId(R.id.refresh_button))
            .perform(click())
        onView(withId(R.id.recyclerView))
            .check(matches(atPosition(0, withText("Montenegro"))))
    }
}

fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
    checkNotNull(itemMatcher)
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: // has no item on such position
                return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}