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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmazingTheme {
                Navbar()
            }
        }
    }
}



data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val cartEmpty: Boolean,
    val badgeCount: Int? = null
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navbar(){
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
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                            },
                            label ={
                                Text(text = item.title)
                            },
                            icon = {
                                BadgedBox(
                                    badge ={
                                        if (item.badgeCount != null) {
                                            Badge {
                                                Text(text = item.badgeCount.toString())
                                            }
                                        }
                                        else if (!item.cartEmpty) {
                                            Badge()
                                        }
                                    } ) {
                                    Icon(
                                        contentDescription = item.title,
                                        imageVector = if (index== selectedItemIndex) item.unselectedIcon else item.selectedIcon
                                    )
                                }
                            }
                        )
                    }
                }
            }){}
    }
}



@Composable
@Preview
fun tryit(){ Navbar()}