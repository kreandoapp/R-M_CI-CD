package com.example.rickandmorty.domain.use_case

import com.example.rickandmorty.data.ResultSealed
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.domain.repositories.CharacterRepository

import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page: Int): ResultSealed<List<Characters>> {
        return repository.getCharacters(page)
    }
}