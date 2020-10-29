package com.tokopedia.filter.view.di

import android.content.Context
import com.tokopedia.filter.view.data.ProductRepository
import com.tokopedia.filter.view.data.RemoteDataSourceImpl
import com.tokopedia.filter.view.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): ProductRepository {

        val remoteRepository = RemoteDataSourceImpl.getInstance(JsonHelper(context))

        return ProductRepository.getInstance(remoteRepository);
    }
}