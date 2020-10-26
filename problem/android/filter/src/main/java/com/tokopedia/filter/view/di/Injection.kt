package com.tokopedia.filter.view.di

import android.content.Context
import com.tokopedia.filter.view.data.ProductRepository
import com.tokopedia.filter.view.data.RemoteDataSource
import com.tokopedia.filter.view.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): ProductRepository {

        val remoteRepository = RemoteDataSource.getInstance(JsonHelper(context))

        return ProductRepository.getInstance(remoteRepository);
    }
}