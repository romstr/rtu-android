package lv.romstr.mobile.rtu_android.shopping

import android.content.Context

class ShoppingRepository(private val context: Context) {

    private val db
        get() = Database.getInstance(context)

    fun getAllShoppingItems() = db.shoppingItemDao().getAll()

}