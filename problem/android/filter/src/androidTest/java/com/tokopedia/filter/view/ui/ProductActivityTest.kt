package com.tokopedia.filter.view.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.tokopedia.filter.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric

class ProductActivityTest {

    @Before
    fun setup(){
        Robolectric.buildActivity(ProductActivity::class.java)
    }


    @Test
    fun loadCourses() {
        Espresso.onView(ViewMatchers.withId(R.id.product_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}