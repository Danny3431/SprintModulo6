package cl.bootcamp.sprintmodulo6.navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.bootcamp.sprintmodulo6.view.DetailsView
import cl.bootcamp.sprintmodulo6.view.HomeView
import cl.bootcamp.sprintmodulo6.viewmodel.ProductViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier)
{
    // Configurar el NavHost
    NavHost(navController = navController, startDestination = "home") {

        // Definir las rutas de navegación
        composable("home") {
            HomeView(navController = navController, viewModel = viewModel)
        }
        composable("details/{productId}") { backStackEntry ->
            // Obtén el `productId` como String y conviértelo a Int
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()

            productId?.let {
                DetailsView(
                    viewModel =viewModel,
                    productId = productId ,
                    navController = navController
                )
            }
        }
    }
}