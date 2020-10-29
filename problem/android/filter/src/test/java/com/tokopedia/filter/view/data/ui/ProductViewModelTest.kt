package com.tokopedia.filter.view.data.ui

import androidx.lifecycle.MutableLiveData
import com.tokopedia.filter.view.data.ProductDataSource
import com.tokopedia.filter.view.data.entity.Product
import com.tokopedia.filter.view.data.entity.Shop
import com.tokopedia.filter.view.ui.ProductViewModel
import com.tokopedia.filter.view.utils.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class ProductViewModelTest {
    private lateinit var viewModel: ProductViewModel

    @Mock
    private lateinit var productRepository: ProductDataSource

    @Mock
    private lateinit var mutable: MutableLiveData<Resource<List<Product>>>

    @Before
    fun setUp() {
        viewModel = ProductViewModel(productRepository, mutable)
    }

    @Test
    fun getProductSuccess() {
        Mockito.`when`<List<Product>>(
            productRepository.getProduct())
            .thenReturn(
                listOf(
                    Product(
                        1,
                        "Samsung",
                        "www",
                    1000,
                    10,
                    10,
                    shop = Shop(1,
                        "test show",
                        "test city"))
                )
            )

        Mockito.`when`(
            mutable.setSuccess(
                listOf(
                    Product(
                        1,
                        "Samsung",
                        "www",
                        1000,
                        10,
                        10,
                        shop = Shop(
                            1,
                            "test show",
                            "test city"
                        )
                    )
                )
            )
        ).thenAnswer{
            Mockito.`when`(mutable.value).thenReturn(it.getArgument(0))
            Unit
        }
        val products = viewModel.getProduct()
        verify<ProductDataSource>(productRepository).getProduct()
        verify(mutable).setSuccess(listOf(
            Product(
                1,
                "Samsung",
                "www",
                1000,
                10,
                10,
                shop = Shop(1,
                    "test show",
                    "test city"))
        ))
        verify(mutable).setLoading()

        Assert.assertNotNull(products)
        Assert.assertEquals(
            Resource(
                ResourceState.SUCCESS,
                listOf(
                    Product(
                        1,
                        "Samsung",
                        "www",
                        1000,
                        10,
                        10,
                        shop = Shop(
                            1,
                            "test show",
                            "test city"
                        )
                    )
                )
            ),
            products.value)
    }

    @Test
    fun getProductFailed() {
        Mockito.`when`<List<Product>>(
            productRepository.getProduct()).then { throw Exception("Error") }
        viewModel.getProduct()

        verify(mutable).setLoading()
        verify(mutable, times(1)).setError()
        verify(mutable, never()).setSuccess(ArgumentMatchers.anyList())
    }
}