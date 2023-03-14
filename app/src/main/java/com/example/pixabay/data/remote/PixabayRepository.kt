package com.example.pixabay.data.remote

import com.example.pixabay.data.remote.models.PixabayResult
import dagger.Provides
import kotlinx.coroutines.flow.Flow

interface PixabayRepository {

    suspend fun getImages(keyword: String, page: Int): PixabayResult

}