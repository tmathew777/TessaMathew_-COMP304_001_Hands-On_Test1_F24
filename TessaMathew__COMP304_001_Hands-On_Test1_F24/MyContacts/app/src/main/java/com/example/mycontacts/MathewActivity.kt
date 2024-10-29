package com.example.mycontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycontacts.ui.theme.MyContactsTheme
import kotlinx.coroutines.launch

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyContactsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactScreen()
                }
            }
        }
    }
}

@Composable
fun ContactScreen(contactViewModel: ContactViewModel = viewModel()) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var selectedType by remember { mutableStateOf("Friend") }

    // State and coroutine scope for snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Contact Type")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = selectedType == "Friend", onClick = { selectedType = "Friend" })
            Text("Friend", modifier = Modifier.padding(start = 8.dp))

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(selected = selectedType == "Family", onClick = { selectedType = "Family" })
            Text("Family", modifier = Modifier.padding(start = 8.dp))

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(selected = selectedType == "Work", onClick = { selectedType = "Work" })
            Text("Work", modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            contactViewModel.addContact(name.text, phoneNumber.text, email.text, selectedType)
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Contact added: ${name.text}")
            }
        }) {
            Text("Add Contact")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(contactViewModel.contacts) { contact ->
                Text(text = "${contact.name}, ${contact.phoneNumber}, ${contact.email}, ${contact.type}")
                Divider()
            }
        }

        // Host for displaying the Snackbar
        SnackbarHost(hostState = snackbarHostState)
    }
}
