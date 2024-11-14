package cl.bootcamp.sprintmodulo6


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import cl.bootcamp.sprintmodulo6.data.ApiService
import cl.bootcamp.sprintmodulo6.data.ProductEntity
import cl.bootcamp.sprintmodulo6.model.Product
import cl.bootcamp.sprintmodulo6.model.ProductDetail
import cl.bootcamp.sprintmodulo6.viewmodel.ProductViewModel
import cl.bootcamp.sprintmodulo6.repository.ProductRepository
import cl.bootcamp.sprintmodulo6.room.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductViewModelTest {

    private lateinit var viewModel: ProductViewModel
    private lateinit var repository: ProductRepository

    @Before
    fun setup() {
        val productDao = FakeProductDao()
        val apiService = FakeApiService()
        repository = ProductRepository(productDao, apiService)
        viewModel = ProductViewModel(repository)
    }

    @Test
    fun testGetProductById() = runBlocking {
        val productId = 1
        viewModel.loadProductDetail(productId)

        val selectedProduct = viewModel.selectedProduct.value
        assertNotNull("El producto no debe ser nulo", selectedProduct)
        assertEquals("El ID del producto debe coincidir", productId, selectedProduct?.id)
    }

    @Test
    fun testRefreshProducts() = runBlocking {
        viewModel.refreshProducts()

        val products = viewModel.allProducts.value
        assertTrue("La lista de productos no debe estar vacía después de refrescar", products.isNotEmpty())
    }
}

 class FakeProductDao : ProductDao {
    private val products = mutableListOf<ProductEntity>()

    override fun getAllProducts(): Flow<List<ProductEntity>> = flowOf(products)

    override suspend fun insertAll(products: List<ProductEntity>) {
        this.products.addAll(products)
    }
}

class FakeApiService : ApiService {
    override suspend fun getProducts(): List<ProductEntity> {
        return listOf(
            ProductEntity(id = 1, name = "Samsung Galaxy A21s 64GB", price = 100.0, image = "https://images.samsung.com/is/image/samsung/es-galaxy-a21s-sm-a217fzkoeub-262755098?"),
            ProductEntity(id = 2, name = "Huawei Nova 7 SE 128GB", price = 200.0, image = "https://consumer.huawei.com/content/dam/huawei-cbg-site/common/mkt/pdp/phones/nova7-se/img/mob/huawei-nova7-se-mob.png")
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