package com.unical.amazing.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unical.amazing.R
import com.unical.amazing.view.account.OrdersHistoryScreen
import com.unical.amazing.view.account.UserProfileScreen
import com.unical.amazing.view.account.WishlistScreen

@Composable
fun AccountView() {
    val accountNavController = rememberNavController()

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        NavHost(navController = accountNavController, startDestination = "main") {
            composable("main") {
                AccountMainScreen(accountNavController)
            }
            composable("profile") {
                UserProfileScreen()
            }
            composable("orders") {
                OrdersHistoryScreen()
            }
            composable("wishlist") {
                WishlistScreen()
            }
        }
    }
}

@Composable
fun AccountMainScreen(navController: NavController) {
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
        AccountOptions(navController)
    }
}




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountOptions(navController: NavController) {
    val items = listOf(
        "I miei dati" to "profile",
        "I miei ordini" to "orders",
        "Liste desideri" to "wishlist"
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
                        navController.navigate(route)
                    }
            ) {
                ListItem(
                    text = { Text(title, fontSize = 18.sp) },
                    icon = {
                        when (route) {
                            "profile" -> Icon(painterResource(id = R.drawable.person_24px), contentDescription = null)
                            "orders" -> Icon(painterResource(id = R.drawable.receipt_long_24px), contentDescription = null)
                            "wishlist" -> Icon(painterResource(id = R.drawable.list_alt_24px), contentDescription = null)
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
            Divider(color = Color.Gray, thickness = 0.5.dp)
        }
    }
}
