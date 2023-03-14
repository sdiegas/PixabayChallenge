package com.example.pixabay.di

import com.example.pixabay.data.remote.CoroutineDispatcherProvider
import com.example.pixabay.data.remote.PixabayApi
import com.example.pixabay.data.remote.PixabayRepository
import com.example.pixabay.data.remote.PixabayRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcherProvider {
        return CoroutineDispatcherProvider()
    }

    @Singleton
    @Provides
    fun providePixabayRepository(pixabayApi: PixabayApi): PixabayRepository {
        return PixabayRepositoryImpl(pixabayApi)
    }
}