package com.unical.amazing.view.account

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unical.amazing.R

@Composable
fun AccountView(onLogout: () -> Unit) {
    val accountNavController = rememberNavController()
    val context = LocalContext.current
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        NavHost(navController = accountNavController, startDestination = "main") {
            composable("main") {
                AccountMainScreen(accountNavController, onLogout)
            }
            composable("profile") {
                UserProfileScreen(context)
            }
            composable("orders") {
                OrdersHistoryScreen(context)
            }
            composable("wishlist") {
                WishlistScreen()
            }
        }
    }
}

@Composable
fun AccountMainScreen(navController: NavController, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Account",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        AccountOptions(navController, onLogout)
    }
}

@SuppressLint("PrivateResource")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountOptions(navController: NavController, onLogout: () -> Unit) {
    val items = listOf(
        "I miei dati" to "profile",
        "I miei ordini" to "orders",
        "Liste desideri" to "wishlist",
        "Logout" to null
    )

    Column {
        items.forEach { (title, route) ->
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        route?.let { navController.navigate(it) } ?: onLogout()
                    }
            ) {
                ListItem(
                    text = { Text(title, fontSize = 18.sp) },
                    icon = {
                        when (route) {
                            "profile" -> Icon(painterResource(id = R.drawable.person_24px), contentDescription = null)
                            "orders" -> Icon(painterResource(id = R.drawable.receipt_long_24px), contentDescription = null)
                            "wishlist" -> Icon(painterResource(id = R.drawable.list_alt_24px), contentDescription = null)
                            null -> Icon(painterResource(id = R.drawable.logout), contentDescription = null)
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
            Divider(color = Color.Gray, thickness = 0.5.dp)
        }
    }
}
