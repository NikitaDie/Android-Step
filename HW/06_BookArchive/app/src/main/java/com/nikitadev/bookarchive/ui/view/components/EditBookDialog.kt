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
fun EditBookDialog(book: Book, onDismiss: () -> Unit, onSave: (Book) -> Unit) {
    var title by remember { mutableStateOf(book.title) }
    var genre by remember { mutableStateOf(book.genre) }
    var year by remember { mutableStateOf(book.publicationYear.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Book") },
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
                onSave(book.copy(
                    title = title,
                    genre = genre,
                    publicationYear = year.toIntOrNull() ?: book.publicationYear
                ))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
