import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.unical.amazing.model.HomeItem
import com.unical.amazing.viewmodel.HomeViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    Scaffold(
        topBar = {SearchBar()}
    ) {
    }

//    val homeItems by homeViewModel.homeItems.collectAsState()
//    val coroutineScope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            homeViewModel.fetchHomeItems()
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.Start
//    ) {
//        Text(text = "Home Screen", modifier = Modifier.padding(bottom = 16.dp))
//
//        LazyColumn {
//            items(homeItems) { item ->
//                HomeItemCard(item)
//            }
//        }
//    }
}

@Composable
fun HomeItemCard(item: HomeItem) {
    // Qui puoi definire il composable per visualizzare un singolo elemento dell'elenco
    Text(text = item.title)
}


@Composable
fun SearchBar(){
    var expanded by remember{ mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "App Title") },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 4.dp,
        navigationIcon = { /* Icona di navigazione, se necessario */ },
        actions = {
            // Campo di ricerca
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}


