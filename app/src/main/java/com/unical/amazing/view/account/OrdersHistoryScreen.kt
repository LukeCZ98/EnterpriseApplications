package com.unical.amazing.view.account

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.viewmodel.AccountViewModel


@Composable
fun OrdersHistoryScreen(accountViewModel: AccountViewModel = viewModel()) {
    val userState = accountViewModel.user.collectAsState()

    val user = userState.value ?: return // Gestisci la situazione in cui l'utente è nullo

    Column {
        Text("Orders History")
        // Aggiungi qui la visualizzazione dello storico degli ordini dell'utente
    }
}
