package com.tokopedia.filter.view.data.data.entity

import com.tokopedia.filter.view.data.entity.LocationFilter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationFilter {

    @Test
    fun getNameWhereCityHaveTo2Value() {
        val dummyData = LocationFilter(1, listOf("Test", "test2"), false)
        Assert.assertEquals("Others", dummyData.getName())
    }

    @Test
    fun getNameWhereCityHaveTo1Value() {
        val dummyData = LocationFilter(1, listOf("Test"), false)
        Assert.assertEquals("Test", dummyData.getName())
    }

    @Test
    fun getNameWhereCityIsEmpty() {
        val dummyData = LocationFilter(1, listOf(), false)
        Assert.assertEquals("", dummyData.getName())
    }

    @Test
    fun getNameWhereCityIsNull() {
        val dummyData = LocationFilter(1, listOf(), false)
        Assert.assertEquals("", dummyData.getName())
    }
}