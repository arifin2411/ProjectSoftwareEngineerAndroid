package com.tokopedia.filter.view.data.data

import com.tokopedia.filter.view.data.ProductRepository
import com.tokopedia.filter.view.data.RemoteDataSource
import com.tokopedia.filter.view.data.response.Data
import com.tokopedia.filter.view.data.response.ProductResponse
import com.tokopedia.filter.view.data.response.Products
import com.tokopedia.filter.view.data.response.Shop
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryTest {

    private val remoteDataSource = Mockito.mock(RemoteDataSource::class.java)
    private fun <T> uninitialized(): T = null as T

    val dataDummy = ProductResponse(Data(listOf(
        Products(
            1,
            "Samsung",
            "www",
            1000,
            10,
            10,
            shop = Shop(1,
                "test show",
                "test city")))
    ))

    private fun <T> anyObject(): T {
        Mockito.anyObject<T>()
        return uninitialized()
    }

    @Test
    fun getProduct() {
        doAnswer{
            (it.getArgument(4) as RemoteDataSource.LoadCallback).onAllProductReceived(dataDummy)
        }.`when`(remoteDataSource).getProduct(anyList(), anyInt(), anyInt(), anyInt(), anyObject())
        val data = ProductRepository(remoteDataSource).getProduct(listOf("jakarta"), 1000, 1000, 1)
        verify(remoteDataSource).getProduct(anyList(), anyInt(), anyInt(), anyInt(),anyObject())
        Assert.assertEquals(1, data.size)
    }

    @Test
    fun getAllProduct() {
        doAnswer{
            (it.getArgument(0) as RemoteDataSource.LoadCallback).onAllProductReceived(dataDummy)
            null
        }.`when`(remoteDataSource).getAllProduct(anyObject())
        val data = ProductRepository(remoteDataSource).getAllProduct()
        verify(remoteDataSource).getAllProduct(anyObject())
        Assert.assertEquals(1, data.size)
    }

    @Test
    fun getAllProductWhenFailed() {
        doThrow(RuntimeException("Error")).`when`(remoteDataSource).getAllProduct(anyObject())
        try {
           ProductRepository(remoteDataSource).getAllProduct()
        } catch (e: Throwable) {
            Assert.assertEquals("Error", e.message)
        }
    }
}