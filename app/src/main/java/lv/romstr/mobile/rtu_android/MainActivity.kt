package lv.romstr.mobile.rtu_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import lv.romstr.mobile.rtu_android.detail.DetailActivity
import lv.romstr.mobile.rtu_android.shopping.ShoppingItem

class MainActivity : AppCompatActivity(), AdapterClickListener {

    private lateinit var adapter: ShoppingItemRecyclerAdapter

    private lateinit var viewModel: MainViewModel

    private val items = mutableListOf<ShoppingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testFiles()

        adapter = ShoppingItemRecyclerAdapter(this, items)
        mainItems.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.shoppingItems.observe(this, Observer(adapter::updateItems))

        mainButtonAdd.setOnClickListener { appendItem() }
    }

    private fun testFiles() {
        val filename = "test.txt"
        val contents = "Hello, file system!"

        // write to file
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(contents.toByteArray())
        }

        // read from file
        val storedContents = openFileInput(filename).bufferedReader().useLines { lines ->
            lines.fold("") { some, text -> "$some\n$text" }
        }

        println(storedContents)

        // list stored files
        fileList().forEach(::println)
    }

    // Not used in this practice
    private fun appendItem() {
//        val item = ShoppingItem(mainEditName.text.toString(), 1, "")
//        item.uid = db.shoppingItemDao().insertAll(item).first()
//        items.add(item)
//
//        items.sortBy { it.name }
//        mainEditName.text.clear()
//        adapter.notifyDataSetChanged()
    }

    override fun itemClicked(item: ShoppingItem) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(EXTRA_ID, item.uid)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    // Not used in this practice
    override fun deleteClicked(item: ShoppingItem) {
//        db.shoppingItemDao().delete(item)
    }

    // Not used in this practice
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_DETAILS && resultCode == RESULT_OK && data != null) {
//            val id = data.getLongExtra(EXTRA_ID, 0)
//            val item = db.shoppingItemDao().getItemById(id)
//            val position = items.indexOfFirst { it.uid == item.uid }
//            items[position] = item
//            adapter.notifyItemChanged(position)
//        }
    }

    companion object {
        const val EXTRA_ID = "lv.romstr.mobile.extras.shopping_item_id"
        const val REQUEST_CODE_DETAILS = 1234
    }
}

interface AdapterClickListener {

    fun itemClicked(item: ShoppingItem)

    fun deleteClicked(item: ShoppingItem)

}