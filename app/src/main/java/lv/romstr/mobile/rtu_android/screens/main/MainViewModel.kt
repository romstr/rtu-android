package lv.romstr.mobile.rtu_android.screens.main

import androidx.lifecycle.ViewModel
import lv.romstr.mobile.rtu_android.ShoppingItem
import lv.romstr.mobile.rtu_android.api.ShoppingRepository

class MainViewModel(private val repository: ShoppingRepository = ShoppingRepository) : ViewModel() {

    fun getItems() = repository.getItems()

    fun createItem(item: ShoppingItem) = repository.createItem(item)

    fun removeItem(id: String) = repository.removeItem(id)

}