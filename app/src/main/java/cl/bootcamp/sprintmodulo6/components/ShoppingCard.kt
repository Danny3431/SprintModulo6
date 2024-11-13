package cl.bootcamp.sprintmodulo6.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cl.bootcamp.sprintmodulo6.model.Product
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale



@Composable
fun ShoppingCard(
    product: Product,
    navController: NavController,
    onClick: () -> Unit
) {

// Usa Coil para cargar la imagen desde la URL
    val painter = rememberAsyncImagePainter(
        model = product.image, // URL o imagen
        contentScale = ContentScale.Crop, // Ajuste de la imagen (opcional)
        filterQuality = FilterQuality.High // Calidad de la imagen (opcional)
    )
    Log.d("ProductImage", "Image URL: ${product.image}")


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 8.dp)
            )
            Log.d("ProductImage", "Image URL: ${product.image}")
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Cambia el color de fondo de la barra de navegación según la pantalla
    val containerColor = when {
        currentRoute == "home" -> MaterialTheme.colorScheme.primary
        currentRoute?.startsWith("details") == true -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.primary // Color por defecto
    }

    NavigationBar(
        containerColor = containerColor,
        contentColor = MaterialTheme.colorScheme.tertiary
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.onPrimary) },
            label = { Text("Home", color = MaterialTheme.colorScheme.onPrimary) },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito",tint = MaterialTheme.colorScheme.onPrimary) },
            label = { Text("Carrito", color = MaterialTheme.colorScheme.onPrimary) },
            selected = currentRoute?.startsWith("details") == true,
            onClick = { navController.navigate("details/1") } // Aquí puedes usar un ID de ejemplo o modificarlo según tu lógica
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil",tint = MaterialTheme.colorScheme.onPrimary) },
            label = { Text("Perfil", color = MaterialTheme.colorScheme.onPrimary) },
            selected = currentRoute == "profile",
            onClick = { navController.navigate("home") } // Temporalmente vuelve a home
        )
    }
}