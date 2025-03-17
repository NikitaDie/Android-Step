package com.nikitadev.contactbook.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikitadev.contactbook.data.Contact

@Composable
fun ContactListScreen(
    contacts: List<Contact>,
    onAddContact: () -> Unit,
    onEditContact: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddContact) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(
                text = "Contacts",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            if (contacts.isEmpty()) {
                Text(
                    text = "No contacts yet",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                ContactList(
                    contacts = contacts,
                    onEditContact = onEditContact,
                    onDeleteContact = onDeleteContact
                )
            }
        }
    }
}

@Composable
private fun ContactList(
    contacts: List<Contact>,
    onEditContact: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit
) {
    LazyColumn {
        items(contacts) { contact ->
            ContactItem(
                contact = contact,
                onEditClick = { onEditContact(contact) },
                onDeleteClick = { onDeleteContact(contact) }
            )
        }
    }
}

@Composable
private fun ContactItem(
    contact: Contact,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(contact.name, style = MaterialTheme.typography.bodyLarge)
                Text(contact.phone, style = MaterialTheme.typography.bodyMedium)
            }
            Row {
                TextButton(onClick = onEditClick) {
                    Text("Edit")
                }
                TextButton(onClick = onDeleteClick) {
                    Text("Delete")
                }
            }
        }
    }
}