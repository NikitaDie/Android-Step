package com.nikitadev.bookarchive.ui.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nikitadev.bookarchive.data.model.Author

@Composable
fun AddAuthorDialog(onDismiss: () -> Unit, onAdd: (Author) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Author") },
        text = {
            Column {
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") })
                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (firstName.isNotBlank() && lastName.isNotBlank()) {
                        onAdd(Author(firstName = firstName, lastName = lastName))
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}