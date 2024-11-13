package cl.bootcamp.sprintmodulo6.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val imageUrl: String? = null // Permite que sea null

)