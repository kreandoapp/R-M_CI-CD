package com.example.rickandmorty.domain.repositories

import com.example.rickandmorty.data.ResultSealed
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.model.Characters

interface CharacterRepository {

    //fun getCharacters(page: Int): Flow<Result<List<Characters>>>
    suspend fun getCharacters(page: Int): ResultSealed<List<Characters>>
    suspend fun getCharacter(id: Int): ResultSealed<Character>

}