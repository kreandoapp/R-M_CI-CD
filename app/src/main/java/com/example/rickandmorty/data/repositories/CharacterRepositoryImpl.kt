package com.example.rickandmorty.data.repositories

import com.example.rickandmorty.data.ResultSealed
import com.example.rickandmorty.data.source.remote.RickAndMortyApi
import com.example.rickandmorty.data.source.remote.dto.toCharacter
import com.example.rickandmorty.data.source.remote.dto.toListCharacters
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.domain.repositories.CharacterRepository

import javax.inject.Inject
import kotlin.Exception

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi
): CharacterRepository{

    override suspend fun getCharacters(page: Int): ResultSealed<List<Characters>> {
        val response = try {
            api.getCharacters(page).toListCharacters()
        }catch (e: Exception){
            return ResultSealed.Error("An unknown error occurred")
        }
        return ResultSealed.Success(response)

        /*emit(Result.Loading())
        try {
            val response = api.getCharacters(page).toListCharacters()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(
                message = "Oops, something went wrong",
                data = null
            ))
        } catch (e: IOException) {
            emit(Result.Error(
                message = "Couldn't reach server, check your internet connection",
                data = null
            ))
        }*/
    }

    override suspend fun getCharacter(id: Int): ResultSealed<Character> {
        val response = try {
            api.getCharacter(id)
        } catch (e: Exception) {
            return ResultSealed.Error("An unknown error occurred")
        }
        return ResultSealed.Success(response.toCharacter())
    }
}