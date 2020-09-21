package lv.romstr.mobile.rtu_android.screens.detail

import androidx.lifecycle.ViewModel
import lv.romstr.mobile.rtu_android.ShoppingItem
import lv.romstr.mobile.rtu_android.api.ShoppingRepository

class DetailViewModel(private val repository: ShoppingRepository = ShoppingRepository) :
    ViewModel() {

    fun getItem(id: String) = repository.getItem(id)

    fun updateItem(id: String, item: ShoppingItem) = repository.updateItem(id, item)

}