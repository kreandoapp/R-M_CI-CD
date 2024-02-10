package com.example.rickandmorty.domain.use_case

import com.example.rickandmorty.data.ResultSealed
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): ResultSealed<Character> {
        return repository.getCharacter(id)
    }
}