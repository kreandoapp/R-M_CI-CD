package com.example.rickandmorty.domain.use_case

import com.example.rickandmorty.data.ResultSealed
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.domain.repositories.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetCharactersUseCaseTest() {

    @RelaxedMockK
    private lateinit var repository: CharacterRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase
    val page = 1

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCharactersUseCase = GetCharactersUseCase(repository)
    }

    @Test
    fun `invoke should return characters list from repository`() = runBlocking {

        val list = listOf(Characters(1, "Rick", "Humano", "foto.jpg", "Vivo"))
        //Given
        coEvery { repository.getCharacters(page) } returns ResultSealed.Success(data = list)
        //When
        val response = repository.getCharacters(page)
        //Then
        assert(response is ResultSealed.Success && response.data == list)
    }

    @Test
    fun `invoke should return characters empty list from repository`() = runBlocking {
       //Given
        coEvery { repository.getCharacters(page) } returns ResultSealed.Success(emptyList())
        //When
        val response = repository.getCharacters(page)
        //Then
        assertTrue(response is ResultSealed.Success && response.data?.isEmpty() == true)
    }

}