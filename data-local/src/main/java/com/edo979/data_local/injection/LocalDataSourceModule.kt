package com.edo979.data_local.injection

import com.edo979.data_local.sources.LocalPostDataSourceImpl
import com.edo979.data_repository.data_source.local.LocalPostDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalPostDataSource(postDataSourceImpl: LocalPostDataSourceImpl): LocalPostDataSource
}