package lv.romstr.mobile.rtu_android.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import lv.romstr.mobile.rtu_android.shopping.ShoppingRepository

class DetailViewModel(
    application: Application, private val shoppingItemId: Long
) : AndroidViewModel(application) {

    private val repository = ShoppingRepository(application)

    val shoppingItem
        get() = repository.getShoppingItemById(shoppingItemId)

}