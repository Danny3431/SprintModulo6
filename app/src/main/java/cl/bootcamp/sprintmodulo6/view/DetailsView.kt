package cl.bootcamp.sprintmodulo6.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cl.bootcamp.sprintmodulo6.viewmodel.ProductViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import cl.bootcamp.sprintmodulo6.components.BottomNavigationBar
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsView(viewModel: ProductViewModel, productId: Int, navController: NavController) {

    viewModel.getProductById(productId)


    val productDetail by viewModel.selectedProduct.collectAsState(initial = null)
    val context = LocalContext.current

    // Variables para el correo usando `productDetail` si está disponible
    val email = "info@novaera.cl"
    val asunto = "Consulta ${productDetail?.name ?: "Producto"} - Id: ${productDetail?.id ?: productId}"
    val mensaje = """Hola, me gustaría obtener más información del móvil ${productDetail?.name} de 
        código ${productDetail?.id}.
         
        Quiero saber cuáles son sus características, precio y formas de pago. 
        Quedo atento a su respuesta solicitada.
    """.trimIndent()
    viewModel.loadProductDetail(productId)

    // Manejar el caso en que el producto no esté disponible
    if (productDetail == null) {
        Text(text = "Cargando detalles del producto...")
        return
    }

    // Mostrar los detalles del producto
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle del Teléfono",
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,

                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondary),
            )
        },
        bottomBar = { BottomNavigationBar(navController) },

        floatingActionButton = {
            // Botón flotante para enviar correo
            FloatingActionButton(onClick = {
                // Crear el intent para enviar el correo
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:") // Solo los correos
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, asunto)
                    putExtra(Intent.EXTRA_TEXT, mensaje)
                }

                // Verificar si hay una aplicación de correo configurada
                if (intent.resolveActivity(context.packageManager) != null) {
                    // Usar Intent.createChooser para forzar la selección de la aplicación
                    context.startActivity(Intent.createChooser(intent, "Selecciona una aplicación de correo"))
                } else {
                    // En caso de que no haya ninguna app de correo configurada, muestra un Toast
                    Toast.makeText(context, "No hay ninguna aplicación de correo disponible.", Toast.LENGTH_SHORT).show()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Enviar correo",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            productDetail?.let { product ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary), // Fondo de la Card
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Mostrar la imagen del producto
                        val imagePainter = rememberAsyncImagePainter(product.image)
                        Image(
                            painter = imagePainter,
                            contentDescription = product.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Nombre: ${product.name}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold)
                        Text(text = "Precio: $${product.price}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold)
                        Text(text = "Descripción: ${product.description}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold)
                        Text(text = "Forma de Pago: ${if (product.credit) "Acepta Crédito" else "Solo Efectivo"}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            textAlign = TextAlign.Left,
                            fontWeight = FontWeight.Bold)
                    }
                }
            } ?: Text(text = "Producto no encontrado")

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}