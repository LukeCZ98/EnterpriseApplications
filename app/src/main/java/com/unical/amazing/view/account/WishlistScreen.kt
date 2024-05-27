package com.unical.amazing.view.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.viewmodel.AccountViewModel

@Composable
fun WishlistScreen(accountViewModel: AccountViewModel = viewModel()) {
    val userState = accountViewModel.user.collectAsState()

    userState.value?.let { user ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Wishlist", style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 16.dp))
            user.wishlist.forEach { wishItem ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                    elevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = wishItem,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Divider(color = Color.Gray, thickness = 0.5.dp)
            }
        }
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}
