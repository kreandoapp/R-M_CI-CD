package com.example.rickandmorty.use_case


import com.example.rickandmorty.data.Result
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.domain.repositories.CharacterRepository
import com.example.rickandmorty.domain.use_case.GetCharactersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test


class GetCharactersUseCaseTest {

    @Test
    fun `invoke should return characters from repository`() = runBlocking {
        // Given
        val page = 1
        val characters = listOf(Characters(1, "Rick", "Human","foto",""))
        val expectedResult = Result.Success(characters)

        val repository: CharacterRepository = mockk {
            coEvery { getCharacters(page) } returns flowOf(expectedResult)
        }

        val useCase = GetCharactersUseCase(repository)

        // When
        val result = useCase(page).first()

        // Then
        assert(expectedResult == result)

    }



}
