import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unical.amazing.swagger.models.ProductDto
import com.unical.amazing.viewmodel.admin.product.ProductViewModel

@Composable
fun AddProductScreen(productViewModel: ProductViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var available by remember { mutableStateOf("") }
    var imgUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = imgUrl,
            onValueChange = { imgUrl = it },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val priceValue = price.toDouble()
                    val newprod = ProductDto(0,title,priceValue,description,true,imgUrl)
                    productViewModel.addProduct(newprod)
                    // Reset fields after adding the product
                    title = ""
                    price = ""
                    description = ""
                    available = ""
                    imgUrl = ""
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Product")
        }
    }
}
