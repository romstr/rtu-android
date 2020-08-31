package lv.romstr.mobile.rtu_android

data class ShoppingItem(
    val name: String,
    val quantity: Int,
    val unit: String,
    val imageUrl: String? = null
)