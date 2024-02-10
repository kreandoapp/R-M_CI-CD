package com.example.rickandmorty.domain.use_case

import com.example.rickandmorty.data.ResultSealed
import com.example.rickandmorty.data.source.remote.dto.Location
import com.example.rickandmorty.data.source.remote.dto.Origin
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repositories.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCharacterUseCaseTest() {
    @RelaxedMockK
    private lateinit var repository: CharacterRepository

    lateinit var getCharacterUseCase: GetCharacterUseCase
    val id = 1

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCharacterUseCase = GetCharacterUseCase(repository)
    }

    @Test
    fun `invoke should return character from repository`() = runBlocking {
        val character = Character(
            1, "Rick", "vivo", "Humano", "Masculino", Origin("asd", "asd"),
            Location("sdsd", "dasd"), "foto.jpg"
        )
        //Given
        coEvery { repository.getCharacter(id) }returns ResultSealed.Success(character)

        //When
        val response = getCharacterUseCase(id)

        //Then
        assertTrue(response is ResultSealed.Success && response.data == character)
    }
}