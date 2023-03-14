package com.example.pixabay.data.remote

import com.example.pixabay.BuildConfig
import com.example.pixabay.data.remote.models.PixabayResult
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET(BuildConfig.API_KEY)

    suspend fun getImages(
        @Query("q") searchPhrase: String?,
        @Query("page") page: Int
    ): PixabayResult
}