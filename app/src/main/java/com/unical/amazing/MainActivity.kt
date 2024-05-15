package com.unical.amazing


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.unical.amazing.theme.AmazingTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.unical.amazing.view.HomeActivity

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            val navController = rememberNavController()

            AmazingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = { NavBar(navController) }
                    ) {
                        NavHost(navController, startDestination = "home") {
                            composable("home") {
                                HomeActivity()
                            }
                            composable("account") {
//                                AccountScreen()
                            }
                            composable("cart") {
//                                CartScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}





//Funzione per generare la barra di ricerca
@Composable
fun NavBar(navController: NavController){
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
        ))
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
            containerColor = Color.White
    ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.route)
                    },
                    label = {
                        Text(text = item.title)
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount != null) {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                } else if (!item.cartEmpty) {
                                    Badge()
                                }
                            }) {
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






