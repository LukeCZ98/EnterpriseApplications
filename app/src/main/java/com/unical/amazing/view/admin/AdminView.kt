package com.unical.amazing.view.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AdminView(onLogout: () -> Unit,adminNavController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { adminNavController.navigate("product") },
            modifier = Modifier.fillMaxWidth(0.8f) // Adjust width as needed
        ) {
            Text("Gestione Prodotti")
        }

        Button(
            onClick = { /*aggiungere funzione*/ },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 8.dp) // Add some space between buttons
        ) {
            Text("Gestione Utenti")
        }

        Button(
            onClick = { onLogout() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 8.dp) // Add some space between buttons
        ) {
            Text("Logout")
        }
    }
}
