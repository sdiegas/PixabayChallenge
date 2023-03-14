package com.example.pixabay

import com.example.pixabay.data.remote.PixabayApi
import com.example.pixabay.data.remote.PixabayRepository
import com.example.pixabay.data.remote.PixabayRepositoryImpl
import com.example.pixabay.data.remote.models.PixabayResult
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Before

class PixabayRepositoryTest {

    private lateinit var api: PixabayApi

    private lateinit var testee: PixabayRepository

    @Before
    fun setup() {
        api = mockk(relaxUnitFun = true)
        testee = PixabayRepositoryImpl(api)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getImages() = runTest {
        coEvery { api.getImages(any(), any()) } returns PixabayResult(listOf(), 0, 0)

        testee.getImages("fruits", 1)

        coVerify { api.getImages("fruits", 1) }
    }
}