package lv.romstr.mobile.rtu_android.screens.main

import androidx.lifecycle.ViewModel
import lv.romstr.mobile.rtu_android.ShoppingItem
import lv.romstr.mobile.rtu_android.api.FirebaseRepository

class MainViewModel(private val repository: FirebaseRepository = FirebaseRepository) : ViewModel() {

    fun getItems() = repository.observeItems()

    fun createItem(item: ShoppingItem) = repository.addItem(item)

    fun removeItem(item: ShoppingItem) = repository.removeItem(item)

}