package lv.romstr.mobile.rtu_android.api

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import lv.romstr.mobile.rtu_android.ShoppingItem

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T> : Resource<T>()
    class Loaded<T> : Resource<T>()
    class Error<T>(val message: String?) : Resource<T>()
}

object ShoppingRepository {

    private val service by lazy { ShoppingServiceProvider.instance }

    fun getItems() = request { service.getItems() }

    fun createItem(item: ShoppingItem) = request { service.saveItem(item) }

    fun removeItem(id: String) = request { service.removeItem(id) }

    fun getItem(id: String) = request { service.getItem(id) }

    fun updateItem(id: String, item: ShoppingItem) = request { service.updateItem(id, item) }

    private fun <T> request(request: suspend () -> T) = liveData<Resource<T>>(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(request()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        } finally {
            emit(Resource.Loaded())
        }
    }
}