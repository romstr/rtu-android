package lv.romstr.mobile.rtu_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val items = mutableListOf(
            ShoppingItem("Bread", 1, "pcs."),
            ShoppingItem("Eggs", 10, "pcs."),
            ShoppingItem("Milk", 1, "l"),
            ShoppingItem("Potatoes", 2, "kg"))

    private lateinit var adapter: ShoppingItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ShoppingItemRecyclerAdapter(items)
        mainItems.adapter = adapter

        mainButtonAdd.setOnClickListener { appendItem() }
    }

    private fun appendItem() {
        items.add(ShoppingItem(mainEditName.text.toString(), 1, ""))
        items.sortBy { it.name }
        mainEditName.text.clear()
        adapter.notifyDataSetChanged()
    }
}