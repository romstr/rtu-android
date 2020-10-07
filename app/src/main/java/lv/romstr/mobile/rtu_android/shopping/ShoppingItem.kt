package lv.romstr.mobile.rtu_android.shopping

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "shopping_item")
data class ShoppingItem(
    val name: String,
    val quantity: Int,
    val unit: String,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
)

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM shopping_item")
    fun getAll(): LiveData<List<ShoppingItem>>

    @Query("SELECT * FROM shopping_item WHERE uid = :itemId")
    fun getItemById(itemId: Long): ShoppingItem

    @Insert
    fun insertAll(vararg items: ShoppingItem): List<Long>

    @Update
    fun update(item: ShoppingItem)

    @Delete
    fun delete(item: ShoppingItem)
}