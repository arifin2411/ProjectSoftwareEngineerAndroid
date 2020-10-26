package com.tokopedia.filter.view.data.ui

import com.tokopedia.filter.view.data.ProductRepository
import com.tokopedia.filter.view.data.entity.Product
import com.tokopedia.filter.view.data.entity.Shop
import com.tokopedia.filter.view.ui.ProductViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductViewModelTest {

    private lateinit var viewModel: ProductViewModel

    @Mock
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        viewModel = ProductViewModel(productRepository)
    }

    @Test
    fun getProduct() {
        `when`<List<Product>>(
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
        val products = viewModel.getProduct()
        verify<ProductRepository>(productRepository).getProduct()
        Assert.assertNotNull(products)
    }

}