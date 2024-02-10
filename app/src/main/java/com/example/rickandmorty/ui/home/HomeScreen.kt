package com.example.rickandmorty.ui.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickandmorty.R
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.ui.home.components.CharacterItem
import com.example.rickandmorty.ui.home.components.SearchBar

@Composable
fun HomeScreen(
    onItemClicked: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val scaffoldState = rememberScaffoldState()


    LaunchedEffect(true) {
        viewModel.getCharacters(false)
    }

    
  /*  LaunchedEffect(key1 = true) {
        viewModel.getCharacters(false)
        *//*eventFlow.collectLatest { event ->
            when (event) {
                is HomeViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }*//*
    }*/

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeTopBar()
        },
        bottomBar = {
            HomeBottomBar(
                showPrevious = state.showPrevious,
                showNext = state.showNext,
                onPreviousPressed = { viewModel.getCharacters(false) },
                onNextPressed = { viewModel.getCharacters(true) }
            )
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.padding(innerPadding),
            onItemClicked = { onItemClicked(it) },
            isLoading = state.isLoading,
            characters = state.characters,

            )
    }
}

@Composable
private fun HomeTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.imagen),
                    modifier = modifier
                        .size(40.dp)
                        .weight(1f),

                    contentDescription = ""
                )
                Text(
                    text = stringResource(id = R.string.home_title),
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .weight(1f)
                        .wrapContentSize(Alignment.Center), fontSize = 15.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.imagen),
                    modifier = modifier
                        .size(40.dp)
                        .weight(1f),
                    contentDescription = ""
                )
            }


        },
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit,
    isLoading: Boolean = false,
    characters: List<Characters> = emptyList(),
) {

    var searchText by remember { mutableStateOf("") }


    val filteredCharacters = characters.filter { character ->
        character.name.contains(searchText, ignoreCase = true)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        SearchBar(
            text = searchText,
            onTextChanged = { newText ->
                searchText = newText
            }
        )


        LazyColumn(
            contentPadding = PaddingValues(vertical = 6.dp),
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(filteredCharacters.size) { index ->
                    CharacterItem(
                        modifier = Modifier.fillMaxWidth(),
                        item = filteredCharacters[index],
                        onItemClicked = { onItemClicked(it) }
                    )
                }
            }
        )
    }

    if (isLoading) FullScreenLoading()
}


@Composable
private fun HomeBottomBar(
    showPrevious: Boolean,
    showNext: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                enabled = showPrevious,
                onClick = onPreviousPressed
            ) {
                Text(text = stringResource(id = R.string.previous_button))
            }
            TextButton(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                enabled = showNext,
                onClick = onNextPressed
            ) {
                Text(text = stringResource(id = R.string.next_button))
            }
        }
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}