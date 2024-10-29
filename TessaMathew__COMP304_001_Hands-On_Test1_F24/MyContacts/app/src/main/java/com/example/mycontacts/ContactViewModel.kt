package com.example.mycontacts

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Contact(val name: String, val phoneNumber: String, val email: String, val type: String)

class ContactViewModel : ViewModel() {
    var contacts = mutableStateListOf<Contact>()
        private set

    fun addContact(name: String, phoneNumber: String, email: String, type: String) {
        contacts.add(Contact(name, phoneNumber, email, type))
    }
}
