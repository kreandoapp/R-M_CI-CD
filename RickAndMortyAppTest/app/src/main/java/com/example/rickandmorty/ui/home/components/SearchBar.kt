package com.example.rickandmorty.ui.home.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SearchBar(
    text: String,
    onTextChanged: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clip(shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        BasicTextField(
            value = text,
            onValueChange = { newText ->
                onTextChanged(newText)
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp),
            cursorBrush = SolidColor(Color.Black)
        )

    }
}
