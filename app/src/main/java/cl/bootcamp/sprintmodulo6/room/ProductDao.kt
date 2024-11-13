package cl.bootcamp.sprintmodulo6.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.bootcamp.sprintmodulo6.data.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // Función para insertar un producto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    // Función para insertar una lista de productos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    // Función para recuperar todos los productos
    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<ProductEntity>>
}