package com.nikitadev.bookarchive.ui.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.nikitadev.bookarchive.data.model.Book

@Composable
fun AddBookDialog(authorId: Long, onDismiss: () -> Unit, onAdd: (Book) -> Unit) {
    var title by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Book") },
        text = {
            Column {
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                TextField(value = genre, onValueChange = { genre = it }, label = { Text("Genre") })
                TextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text("Publication Year") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(
                    Book(
                        title = title,
                        genre = genre,
                        publicationYear = year.toIntOrNull() ?: 0,
                        authorId = authorId
                    )
                )
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
