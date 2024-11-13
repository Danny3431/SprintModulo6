package cl.bootcamp.sprintmodulo6.model

import cl.bootcamp.sprintmodulo6.data.ProductEntity

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String
)

data class ProductDetail(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val description: String,
    val lastPrice: Double,
    val credit: Boolean
)

// Mueve esta función fuera de ProductDetail para que esté a nivel superior
fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        image = this.image ?: "" // Usa un valor por defecto si es null
    )
}