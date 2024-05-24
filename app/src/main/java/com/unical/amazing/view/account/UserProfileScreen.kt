package com.unical.amazing.view.account

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.viewmodel.AccountViewModel


@Composable
fun UserProfileScreen(accountViewModel: AccountViewModel = viewModel()) {
    val userState = accountViewModel.user.collectAsState()

    val user = userState.value ?: return // Gestisci la situazione in cui l'utente è nullo

    Column {
        UserProfileSection(user)
        // Aggiungi qui altri campi per la modifica dei dati dell'utente, se necessario
    }
}
