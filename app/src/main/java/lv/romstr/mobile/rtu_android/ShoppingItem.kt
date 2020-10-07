package lv.romstr.mobile.rtu_android

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoppingItem(
    val name: String = "",
    val quantity: Int = 0,
    val unit: String = "",
    var id: String = ""
): Parcelable