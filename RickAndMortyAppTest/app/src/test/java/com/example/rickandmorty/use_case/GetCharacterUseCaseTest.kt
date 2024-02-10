package com.example.rickandmorty.use_case

import com.example.rickandmorty.data.Result
import com.example.rickandmorty.data.source.remote.dto.Location
import com.example.rickandmorty.data.source.remote.dto.Origin
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repositories.CharacterRepository
import com.example.rickandmorty.domain.use_case.GetCharacterUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCharacterUseCaseTest {

    @Test
    fun `invoke should return character from repository`() = runBlocking {
        // Given
        val characterId = 1
        val origin = Origin("Marte","www.google.com")
        val location = Location("Casa","casa.com")
        val character = Character(1, "Rick", "Human", "Earth","",origin,location,"foto")
        val expectedResult = Result.Success(character)

        val repository: CharacterRepository = mockk {
            coEvery { getCharacter(characterId) } returns expectedResult
        }

        val useCase = GetCharacterUseCase(repository)

        // When
        val result = useCase(characterId)

        // Then
        assertEquals(expectedResult, result)
    }
}
