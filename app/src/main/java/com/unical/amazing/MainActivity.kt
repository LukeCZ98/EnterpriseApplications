package com.unical.amazing

import AddProductScreen
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.unical.amazing.theme.AmazingTheme
import com.unical.amazing.view.account.AccountView
import com.unical.amazing.view.admin.AdminView
import com.unical.amazing.view.admin.EditProductView
import com.unical.amazing.view.admin.ProductManagementView
import com.unical.amazing.viewmodel.admin.product.SharedProductViewModel
import com.unical.amazing.view.auth.AuthScreen
import com.unical.amazing.view.cart.CartView
import com.unical.amazing.view.home.ProductDetailView
import com.unical.amazing.view.home.HomeView
import com.unical.amazing.view.home.SearchResultsView
import com.unical.amazing.viewmodel.admin.product.ProductViewModel
import com.unical.amazing.viewmodel.auth.AuthViewModel
import com.unical.amazing.viewmodel.cart.CartManager
import com.unical.amazing.viewmodel.factories.ProductManagementViewModelFactory
import com.unical.amazing.viewmodel.home.HomeViewModel

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var cartManager: CartManager

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartManager = CartManager(applicationContext)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        setContent {
            val context: Context = LocalContext.current
            val authNavController = rememberNavController()
            val mainNavController = rememberNavController()
            val adminNavController = rememberNavController()
            val viewmodel = remember { HomeViewModel(context) }
            val sharedProductViewModel: SharedProductViewModel = viewModel()

            val isLoggedIn = rememberSaveable { mutableStateOf(checkLoginStatus()) }
            val isAdmin = rememberSaveable { mutableStateOf(false) }

            // Effetto colaterale per aggiornare isAdmin quando isLoggedIn cambia
            LaunchedEffect(isLoggedIn.value) {
                if (isLoggedIn.value) {
                    isAdmin.value = checkAdminStatus(context)
                } else {
                    isAdmin.value = false
                }
            }

            AmazingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (isLoggedIn.value) {
                        if (isAdmin.value) {
                            AdminNavHost(context,adminNavController,sharedProductViewModel) {
                                logout(isLoggedIn)
                            }
                        } else {
                            MainNavHost(cartManager, context, mainNavController, viewmodel) {
                                logout(isLoggedIn)
                            }
                        }
                    } else {
                        AuthNavHost(authNavController, isLoggedIn) { username, password, email, firstName, lastName ->
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

    private fun checkAdminStatus(context: Context): Boolean {
        return AuthViewModel(context).getRole()
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
            AuthScreen(authNavController, isLoggedIn, register)
        }
        // Non è necessario un composable separato per la registrazione se si utilizza una singola schermata
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainNavHost(cartManager: CartManager, context: Context, mainNavController: NavHostController, viewmodel: HomeViewModel, onLogout: () -> Unit) {
    val cartItemCount by cartManager.getCartItemCount().collectAsState(initial = 0)

    Scaffold(
        bottomBar = { NavBar(mainNavController, cartItemCount) }
    ) {
        NavHost(mainNavController, startDestination = "home") {
            composable("home") {
                HomeView(viewmodel, mainNavController, context)
            }
            composable("account") {
                AccountView(onLogout)
            }
            composable("cart") {
                CartView(cartManager, context)
            }
            composable("productDetail/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull()
                val product = viewmodel.productList.find { it.id == productId }
                product?.let {
                    ProductDetailView(it, cartManager)
                }
            }
            composable("searchResults/{query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                SearchResultsView(viewmodel, mainNavController, query, context)
            }
        }
    }
}

@Composable
fun AdminNavHost(
    context: Context,
    adminNavController: NavHostController,
    sharedProductViewModel: SharedProductViewModel,
    onLogout: () -> Unit
) {
    val viewModelFactory = remember { ProductManagementViewModelFactory(context) }
    val viewModel: ProductViewModel = viewModel(factory = viewModelFactory)

    NavHost(adminNavController, startDestination = "adminHome") {
        composable("adminHome") {
            AdminView(onLogout, adminNavController)
        }
        composable("product") {
            ProductManagementView(context, adminNavController, sharedProductViewModel)
        }
        composable("addproduct") {
            AddProductScreen(viewModel)
        }
        composable("editproduct") {
            EditProductView(context, adminNavController, sharedProductViewModel)
        }
    }
}



@Composable
fun NavBar(navController: NavController, cartItemCount: Int) {
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
            cartEmpty = cartItemCount == 0,
            badgeCount = if (cartItemCount > 0) cartItemCount else null
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
