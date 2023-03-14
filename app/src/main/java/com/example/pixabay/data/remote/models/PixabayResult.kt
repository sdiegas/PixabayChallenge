package com.example.pixabay.data.remote.models

data class PixabayResult(
    val hits: List<PixabayImage>,
    val total: Int,
    val totalHits: Int
)
