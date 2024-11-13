package cl.bootcamp.sprintmodulo6.viewmodel


import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.bootcamp.sprintmodulo6.model.Product
import cl.bootcamp.sprintmodulo6.model.ProductDetail
import cl.bootcamp.sprintmodulo6.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    // Flujo de todos los productos
    val allProducts: Flow<List<Product>> = repository.allProducts

    // Función para actualizar los productos desde la API y guardarlos en la base de datos
    fun refreshProducts() {
        viewModelScope.launch {
            try {
                repository.refreshProducts() // Llamamos al repositorio para obtener los productos y guardarlos
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Producto seleccionado para mostrar detalles
    private val _selectedProduct = MutableStateFlow<ProductDetail?>(null)
    val selectedProduct: StateFlow<ProductDetail?> = _selectedProduct.asStateFlow()

    // Cargar detalles de un producto en `_selectedProduct`
    fun loadProductDetail(id: Int) = viewModelScope.launch {
        try {
            val productDetail = repository.getProductDetail(id)
            _selectedProduct.value = productDetail
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Obtener un producto por ID
    fun getProductById(productId: Int) {
        loadProductDetail(productId)
    }

    // Borrar el producto seleccionado
    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }
}