package lv.romstr.mobile.rtu_android

data class ShoppingItem(
    val name: String,
    val quantity: Int,
    val unit: String,
    val imageUrl: String? = null
)

data class CatImage(
    val id: String,
    val url: String,
    val width: Long,
    val height: Long
)
