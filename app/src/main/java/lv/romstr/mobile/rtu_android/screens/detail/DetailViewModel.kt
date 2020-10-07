package lv.romstr.mobile.rtu_android.screens.detail

import androidx.lifecycle.ViewModel
import lv.romstr.mobile.rtu_android.ShoppingItem
import lv.romstr.mobile.rtu_android.api.FirebaseRepository

class DetailViewModel(private val repository: FirebaseRepository = FirebaseRepository) :
    ViewModel() {

    fun getItem(id: String) = repository.getItemById(id)

    fun updateItem(item: ShoppingItem) = repository.updateItem(item)

}