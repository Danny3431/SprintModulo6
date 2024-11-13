package cl.bootcamp.sprintmodulo6.repository


import cl.bootcamp.sprintmodulo6.data.ApiService
import cl.bootcamp.sprintmodulo6.data.ProductEntity
import cl.bootcamp.sprintmodulo6.model.Product
import cl.bootcamp.sprintmodulo6.model.ProductDetail
import cl.bootcamp.sprintmodulo6.model.toEntity
import cl.bootcamp.sprintmodulo6.room.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val apiService: ApiService
) {


    // Convertir el flujo de ProductEntity a Product en el repositorio
    val allProducts: Flow<List<Product>> = productDao.getAllProducts().map { entities ->
        entities.map { entity ->
            Product(
                id = entity.id,
                name = entity.name,
                price = entity.price,
                image = entity.image ?: ""
            )
        }
    }

    suspend fun refreshProducts() {
        try {
            val products = apiService.getProducts() // Obtén los productos desde la API
            productDao.insertAll(products.map { it.toEntity() }) // Inserta los productos en la base de datos
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getProductDetail(id: Int): ProductDetail {
        return apiService.getProductDetail(id)
    }
}
