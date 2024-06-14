package com.unical.amazing

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unical.amazing.theme.AmazingTheme
import com.unical.amazing.view.account.AccountView
import com.unical.amazing.view.auth.AuthScreen
import com.unical.amazing.view.cart.CartView
import com.unical.amazing.view.cart.ProductDetailView
import com.unical.amazing.view.home.HomeView
import com.unical.amazing.view.home.SearchResultsView
import com.unical.amazing.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        setContent {
            val context: Context = LocalContext.current
            val authNavController = rememberNavController()
            val mainNavController = rememberNavController()
            val viewmodel = remember { HomeViewModel(context) }
            val isLoggedIn = rememberSaveable { mutableStateOf(checkLoginStatus()) }

            AmazingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (isLoggedIn.value) {
                        MainNavHost(mainNavController, viewmodel){
                            logout(isLoggedIn)
                        }
                    } else {
                        AuthNavHost(authNavController,isLoggedIn) { username, password, email, firstName, lastName ->
                            register(username, password, email, firstName, lastName, isLoggedIn)
                        }

                    }
                }
            }
        }
    }

    private fun checkLoginStatus(): Boolean {
        val username = sharedPreferences.getString("username", null)
        val password = sharedPreferences.getString("password", null)
        return !username.isNullOrEmpty() && !password.isNullOrEmpty()
    }

    private fun register(username: String, password: String, email: String, firstName: String, lastName: String, isLoggedIn: MutableState<Boolean>) {
        // Simulate registration and save the credentials

    }


    private fun logout(isLoggedIn: MutableState<Boolean>) {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
        isLoggedIn.value = false
        recreate()
    }

}

@Composable
fun AuthNavHost(
    authNavController: NavHostController,
    isLoggedIn: MutableState<Boolean>,
    register: (String, String, String, String, String) -> Unit
) {
    NavHost(authNavController, startDestination = "login") {
        composable("login") {
            AuthScreen(authNavController,isLoggedIn, register)
        }
        // Non è necessario un composable separato per la registrazione se si utilizza una singola schermata
    }
}




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainNavHost(mainNavController: NavHostController, viewmodel: HomeViewModel, onLogout: () -> Unit) {
    Scaffold(
        bottomBar = { NavBar(mainNavController) }
    ) {
        NavHost(mainNavController, startDestination = "home") {
            composable("home") {
                HomeView(viewmodel, mainNavController)
            }
            composable("account") {
                AccountView(onLogout)
            }
            composable("cart") {
                CartView()
            }
            composable("productDetail/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull()
                val product = viewmodel.productList.find { it.id == productId }
                product?.let {
                    ProductDetailView(it)
                }
            }
            composable("searchResults/{query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                SearchResultsView(viewmodel, mainNavController, query)
            }
        }
    }
}

@Composable
fun NavBar(navController: NavController) {
    data class BottomNavigationItem(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val cartEmpty: Boolean,
        val badgeCount: Int? = null,
        val route: String = title
    )

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.home_sel),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.home),
            cartEmpty = true
        ),
        BottomNavigationItem(
            title = "Account",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.acc_sel),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.acc),
            cartEmpty = true
        ),
        BottomNavigationItem(
            title = "Cart",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.shop_sel),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.shop),
            cartEmpty = true,
            badgeCount = 0
        )
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(containerColor = Color.White) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },
                label = { Text(text = item.title) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge { Text(text = item.badgeCount.toString()) }
                            } else if (!item.cartEmpty) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            contentDescription = item.title,
                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon
                        )
                    }
                }
            )
        }
    }
}
