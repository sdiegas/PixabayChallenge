package com.example.pixabay.data.remote

import com.example.pixabay.data.remote.models.PixabayResult
import javax.inject.Inject

class PixabayRepositoryImpl @Inject constructor(
    private val pixabayApi: PixabayApi
) : PixabayRepository {

    override suspend fun getImages(keyword: String, page: Int): PixabayResult {
       return pixabayApi.getImages(searchPhrase = keyword, page = page)
    }
}