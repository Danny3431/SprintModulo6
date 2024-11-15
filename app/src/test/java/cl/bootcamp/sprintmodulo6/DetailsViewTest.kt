package cl.bootcamp.sprintmodulo6


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import cl.bootcamp.sprintmodulo6.data.ApiService
import cl.bootcamp.sprintmodulo6.data.ProductEntity
import cl.bootcamp.sprintmodulo6.model.Product
import cl.bootcamp.sprintmodulo6.model.ProductDetail
import cl.bootcamp.sprintmodulo6.repository.ProductRepository
import cl.bootcamp.sprintmodulo6.room.ProductDao
import cl.bootcamp.sprintmodulo6.view.DetailsView
import cl.bootcamp.sprintmodulo6.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DetailsViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: ProductViewModel
    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        val productDao = FakeProductDao()
        val apiService = FakeApiService()
        val repository = ProductRepository(productDao, apiService)
        viewModel = ProductViewModel(repository)

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `Mostrar detalles del producto`() {
        val sampleProduct = ProductDetail(
            id = 1,
            name = "Samsung Galaxy A21s 64GB",
            price = 100.0,
            image = "",
            description = "https://images.samsung.com/is/image/samsung/es-galaxy-a21s-sm-a217fzkoeub-262755098?",
            credit = true,
            lastPrice = 90.0
        )

        // Configura el ViewModel con el producto simulado
        viewModel.loadProductDetail(sampleProduct.id)

        // Inicia la vista con el navController simulado
        composeTestRule.setContent {
            DetailsView(viewModel = viewModel, productId = sampleProduct.id, navController = navController)
        }

        // Verifica que los elementos de la vista se muestren correctamente
        composeTestRule.onNodeWithText("Nombre: ${sampleProduct.name}").assertIsDisplayed()
        composeTestRule.onNodeWithText("Descripción: ${sampleProduct.description}").assertIsDisplayed()
        composeTestRule.onNodeWithText("Precio: $${sampleProduct.price}").assertIsDisplayed()
    }
}

// Fake Dao para pruebas
class FakeProductDao : ProductDao {
    private val products = mutableListOf<ProductEntity>()

    override fun getAllProducts(): Flow<List<ProductEntity>> = flowOf(products)

    override suspend fun insertAll(products: List<ProductEntity>) {
        this.products.addAll(products)
    }

    override suspend fun insert(product: ProductEntity) {
        this.products.add(product)
    }
}

// Fake ApiService para pruebas
class FakeApiService : ApiService {
    override suspend fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                name = "Samsung Galaxy A21s 64GB",
                price = 100.0,
                image = "https://images.samsung.com/is/image/samsung/es-galaxy-a21s-sm-a217fzkoeub-262755098"
            ),
            Product(
                id = 2,
                name = "Huawei Nova 7 SE 128GB",
                price = 200.0,
                image = "https://consumer.huawei.com/content/dam/huawei-cbg-site/common/mkt/pdp/phones/nova7-se/img/mob/huawei-nova7-se-mob.png"
            )
        )
    }

    override suspend fun getProductDetail(id: Int): ProductDetail {
        return ProductDetail(
            id = id,
            name = "Producto de Prueba",
            price = 100.0,
            image = "",
            description = "Descripción del producto de prueba",
            credit = true,
            lastPrice = 90.0
        )
    }
}
