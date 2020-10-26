package com.tokopedia.filter.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tokopedia.filter.view.data.ProductRepository
import com.tokopedia.filter.view.di.Injection
import com.tokopedia.filter.view.ui.ProductViewModel

class ViewModelFactory private constructor(
    private val mProductRepository: ProductRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                ProductViewModel(mProductRepository) as T
            }
            else -> throw Throwable("Unknown")
        }
    }

}