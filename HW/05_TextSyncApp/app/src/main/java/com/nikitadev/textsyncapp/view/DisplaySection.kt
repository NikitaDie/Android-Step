package com.nikitadev.textsyncapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikitadev.textsyncapp.viewmodel.SharedViewModel

@Composable
fun DisplaySection(viewModel: SharedViewModel) {
    val text by viewModel.text.observeAsState("")

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Вивід тексту:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text)
    }
}