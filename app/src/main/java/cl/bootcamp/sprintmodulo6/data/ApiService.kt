package cl.bootcamp.sprintmodulo6.data

import cl.bootcamp.sprintmodulo6.model.Product
import cl.bootcamp.sprintmodulo6.model.ProductDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product> // List<Product>

    @GET("details/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): ProductDetail // ProductDetail
}