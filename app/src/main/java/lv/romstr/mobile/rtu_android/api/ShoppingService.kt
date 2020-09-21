package lv.romstr.mobile.rtu_android.api

import lv.romstr.mobile.rtu_android.ItemId
import lv.romstr.mobile.rtu_android.ShoppingItem
import retrofit2.http.*

interface ShoppingService {

    @GET("items")
    suspend fun getItems(): List<ShoppingItem>

    @GET("items/{id}")
    suspend fun getItem(@Path("id") id: String): ShoppingItem

    @POST("items")
    suspend fun saveItem(@Body item: ShoppingItem): ItemId

    @DELETE("items/{id}")
    suspend fun removeItem(@Path("id") id: String)

    @PUT("items/{id}")
    suspend fun updateItem(
        @Path("id") id: String,
        @Body item: ShoppingItem
    )

}