package com.nikitadev.contactbook.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.nikitadev.contactbook.data.Contact

class ContactViewModel : ViewModel() {
    val contacts = mutableStateListOf<Contact>()
    private var nextId = 0

    fun addContact(name: String, phone: String) {
        contacts.add(Contact(nextId++, name, phone))
    }

    fun updateContact(id: Int, name: String, phone: String) {
        val index = contacts.indexOfFirst { it.id == id }
        if (index != -1) {
            contacts[index] = Contact(id, name, phone)
        }
    }

    fun deleteContact(contact: Contact) {
        contacts.remove(contact)
    }
}