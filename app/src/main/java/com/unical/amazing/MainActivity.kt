package com.unical.amazing

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.unical.amazing.theme.AmazingTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat



class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            AmazingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = { NavBar() },
                        topBar = { SearchBar() }
                    ) {}
                }
            }
        }
    }
}





//Funzioni per generare la barra di navigazione e la barra di ricerca
@Composable
fun NavBar(){
    data class BottomNavigationItem(
        val title: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector,
        val cartEmpty: Boolean,
        val badgeCount: Int? = null
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
                                imageVector = if (index == selectedItemIndex) item.unselectedIcon else item.selectedIcon
                            )
                        }
                    }
                )
            }
        }
}


@Composable
fun SearchBar(){
    TopAppBar(
        title = { Text(text = "App Title") },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = { /* Icona di navigazione, se necessario */ },
        actions = {
            // Campo di ricerca
            IconButton(onClick = { /* Azione di ricerca */ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}




