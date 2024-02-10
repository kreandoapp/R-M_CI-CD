package com.example.rickandmorty.ui.home


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.ResultSealed
import com.example.rickandmorty.domain.use_case.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
): ViewModel() {

    var state by mutableStateOf(HomeState(isLoading = true))
        private set

    private var currentPage = 1

  /*  init {
        getCharacters(increase = false)
    }*/

    fun getCharacters(increase: Boolean) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            if(increase) currentPage++ else if(currentPage > 1) currentPage--


            val showPrevious = currentPage > 1
            val showNext = currentPage < 42

            val result = getCharactersUseCase(currentPage)

            when(result){
                is ResultSealed.Success -> {
                    state = state.copy(
                        characters = result.data ?: emptyList(),
                        isLoading = false,
                        showPrevious = showPrevious,
                        showNext = showNext
                    )
                }
                is ResultSealed.Error -> {
                    state = state.copy(
                        isLoading = false
                    )

                }
                is ResultSealed.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }
             }
        }
    }
}