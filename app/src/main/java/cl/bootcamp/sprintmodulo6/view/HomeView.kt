package cl.bootcamp.sprintmodulo6.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import cl.bootcamp.sprintmodulo6.components.BottomNavigationBar
import cl.bootcamp.sprintmodulo6.viewmodel.ProductViewModel
import cl.bootcamp.sprintmodulo6.components.ShoppingCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavHostController, viewModel: ProductViewModel) {
    val products = viewModel.allProducts.collectAsState(initial = emptyList()).value
    Log.d("HomeView", "Productos recibidos: $products")  // Agregar un log para ver los productos

    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }



    LaunchedEffect(true) {
        try {
            val products = viewModel.refreshProducts()  // Asumiendo que fetchProducts retorna los productos
            products?.let {
                // Actualiza el estado de los productos
                isLoading.value = false
            }
        } catch (e: Exception) {
            errorMessage.value = "Error cargando productos"
            isLoading.value = false
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bienvenidos a MobiStore",
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->

        if (isLoading.value) {
            CircularProgressIndicator()
        } else if (errorMessage.value != null) {
            Text(errorMessage.value!!)
        } else
            if (products.isEmpty()) {
                Text("No hay productos disponibles")
            } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(products) { product ->
                        ShoppingCard(
                            product = product,
                            navController = navController,
                            onClick = { navController.navigate("details/${product.id}") }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                }

            }

        }
    }
}