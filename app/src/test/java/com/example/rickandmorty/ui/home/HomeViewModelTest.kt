package com.example.rickandmorty.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rickandmorty.data.ResultSealed
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.domain.use_case.GetCharactersUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @RelaxedMockK
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()


    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        homeViewModel = HomeViewModel(getCharactersUseCase)
    }


    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun `getCharacters should update state correctly on success`() = runTest {

        val characterList = listOf(
            Characters(1, "Rick", "Humano", "foto.jpg", "vivo")
        )
        // Given
        coEvery { getCharactersUseCase(1) } returns ResultSealed.Success(characterList)

        // When
        homeViewModel.getCharacters(false)
        // Then
        dispatcher.scheduler.advanceUntilIdle()
        assert(homeViewModel.state.characters == characterList)
        coVerify(exactly = 1) { getCharactersUseCase(1) }
    }

    @Test
    fun `getCharacters should set isLoading to false when success error`() = runTest {
        //Given
        coEvery { getCharactersUseCase(1) } returns ResultSealed.Error("")
        //When
        homeViewModel.getCharacters(false)

        dispatcher.scheduler.advanceUntilIdle()
        //Then
        assertFalse(homeViewModel.state.isLoading)
        assertTrue(homeViewModel.state.characters.isEmpty())
        coVerify(exactly = 1) { getCharactersUseCase(1) }
    }

    @Test
    fun `getCharacters should set isLoading to true when success isloading`() = runTest {
        //Given
        coEvery { getCharactersUseCase(1) } returns ResultSealed.Loading()
        //When
        homeViewModel.getCharacters(false)

        dispatcher.scheduler.advanceUntilIdle()
        //Then
        assertTrue(homeViewModel.state.isLoading)
        assertTrue(homeViewModel.state.characters.isEmpty())
        coVerify(exactly = 1) { getCharactersUseCase(1) }
    }

    @Test
    fun `getCharacters should load next page when increase is true`() = runTest {
        val currentPage = 1
        //Given
        coEvery { getCharactersUseCase(currentPage) } returns ResultSealed.Success(emptyList())
        //When
        homeViewModel.getCharacters(true)
        dispatcher.scheduler.advanceUntilIdle()
        //Then
        assertTrue(homeViewModel.state.isLoading)
        coVerify(exactly = 1) { getCharactersUseCase(currentPage + 1) }
    }

    @Test
    fun `getCharacters should load previous page when increase is false and currentPage is greater than 1`() =
        runTest {
            val currentPage = 2
            //Given
            coEvery { getCharactersUseCase(currentPage) } returns ResultSealed.Success(emptyList())
            //When
            homeViewModel.getCharacters(false)
            dispatcher.scheduler.advanceUntilIdle()
            //Then
            assertTrue(homeViewModel.state.isLoading)
            coVerify(exactly = 1) { getCharactersUseCase(currentPage - 1) }
        }
}