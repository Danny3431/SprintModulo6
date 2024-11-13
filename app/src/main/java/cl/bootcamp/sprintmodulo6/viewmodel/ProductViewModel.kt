package cl.bootcamp.sprintmodulo6.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.bootcamp.sprintmodulo6.model.Product
import cl.bootcamp.sprintmodulo6.model.ProductDetail
import cl.bootcamp.sprintmodulo6.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    // Estado de lista de productos
    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    // Estado de producto seleccionado
    private val _selectedProduct = MutableStateFlow<ProductDetail?>(null)
    val selectedProduct: StateFlow<ProductDetail?> = _selectedProduct.asStateFlow()

    // Estado de carga y errores
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Inicializar productos al iniciar
    init {
        refreshProducts()
    }

    // Función para actualizar productos y manejar estado de carga y error
    fun refreshProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refreshProducts()
                _allProducts.value = repository.allProducts.first() // Solo obtiene una vez, puedes ajustar según tus necesidades
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Cargar detalles de un producto
    fun loadProductDetail(id: Int) = viewModelScope.launch {
        try {
            val productDetail = repository.getProductDetail(id)
            _selectedProduct.value = productDetail
        } catch (e: Exception) {
            _error.value = e.message
        }
    }

    // Limpiar el producto seleccionado
    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }
}