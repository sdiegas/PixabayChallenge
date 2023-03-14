package com.example.pixabay

import com.example.pixabay.data.remote.PixabayRepository
import com.example.pixabay.ui.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

    private lateinit var pixabayRepository: PixabayRepository

    private lateinit var testee: SearchViewModel

    @Before
    fun setup() {
        pixabayRepository = mockk(relaxUnitFun = true)
        testee = SearchViewModel(pixabayRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getImages_Exception() = runTest {
        coEvery { pixabayRepository.getImages(any(), any()) } throws Exception()

        testee.updateSearchQuery("fruits")
        val result = testee.uiState.first()

        //coVerify { pixabayRepository.getImages("fruits", 1) }
        Assert.assertEquals(SearchViewModel.SearchUiState.Error(message = ""), result)
    }

}