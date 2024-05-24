package com.unical.amazing.view

// AccountView.kt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unical.amazing.view.account.OrdersHistoryScreen
import com.unical.amazing.view.account.UserProfileScreen
import com.unical.amazing.view.account.UserProfileSection
import com.unical.amazing.view.account.WishlistScreen
import com.unical.amazing.viewmodel.AccountViewModel

@Composable
fun AccountView(accountViewModel: AccountViewModel = viewModel()) {
    val userState = accountViewModel.user.collectAsState()

    if (userState.value == null) {
        // Mostra uno stato di caricamento o un messaggio di errore se i dati dell'utente non sono stati caricati
        // Potresti voler mostrare uno spinner di caricamento o un messaggio di errore qui
    } else {
        val user = userState.value

        // Visualizza i dati dell'utente e le opzioni di navigazione
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            UserProfileSection(user)
            Spacer(modifier = Modifier.height(16.dp))
            AccountOptions(accountViewModel)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountOptions(accountViewModel: AccountViewModel) {
    val navController = rememberNavController()

    val items = listOf(
        "Profile" to "profile",
        "Orders History" to "orders",
        "Wishlist" to "wishlist"
    )

    Column {
        items.forEach { (title, route) ->
            ListItem(
                text = { Text(title) },
                modifier = Modifier.clickable {
                    navController.navigate(route)
                }
            )
        }

        NavHost(navController, startDestination = "profile") {
            composable("profile") {
                UserProfileScreen(accountViewModel)
            }
            composable("orders") {
                OrdersHistoryScreen(accountViewModel)
            }
            composable("wishlist") {
                WishlistScreen(accountViewModel)
            }
        }
    }
}
