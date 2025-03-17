package com.nikitadev.contactbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikitadev.contactbook.ui.components.ContactEditScreen
import com.nikitadev.contactbook.ui.components.ContactListScreen
import com.nikitadev.contactbook.viewmodel.ContactViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsApp(viewModel)
        }
    }
}

@Composable
fun ContactsApp(viewModel: ContactViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ContactListScreen(
                contacts = viewModel.contacts,
                onAddContact = { navController.navigate("edit/-1") },
                onEditContact = { contact -> navController.navigate("edit/${contact.id}") },
                onDeleteContact = { contact -> viewModel.deleteContact(contact) }
            )
        }
        composable("edit/{contactId}") { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId")?.toInt() ?: -1
            val contact =
                if (contactId != -1) viewModel.contacts.find { it.id == contactId } else null

            ContactEditScreen(
                contact = contact,
                onSave = { name, phone ->
                    if (contact == null) {
                        viewModel.addContact(name, phone)
                    } else {
                        viewModel.updateContact(contact.id, name, phone)
                    }
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}