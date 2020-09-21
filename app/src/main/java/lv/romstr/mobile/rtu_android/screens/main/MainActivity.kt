package lv.romstr.mobile.rtu_android.screens.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import lv.romstr.mobile.rtu_android.R
import lv.romstr.mobile.rtu_android.ShoppingItem
import lv.romstr.mobile.rtu_android.ShoppingItemRecyclerAdapter
import lv.romstr.mobile.rtu_android.api.Resource
import lv.romstr.mobile.rtu_android.screens.detail.DetailActivity

class MainActivity : AppCompatActivity(), AdapterClickListener {

    private val items = mutableListOf<ShoppingItem>()

    private lateinit var adapter: ShoppingItemRecyclerAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ShoppingItemRecyclerAdapter(this, items)
        mainItems.adapter = adapter

        refresh()

        mainButtonAdd.setOnClickListener { appendItem() }

        refreshLayout.setOnRefreshListener { refresh() }
    }

    private fun appendItem() {
        val item = ShoppingItem(mainEditName.text.toString(), 1, "")
        viewModel.createItem(item).observe(this, Observer { refresh() })
        mainEditName.text.clear()
        adapter.notifyDataSetChanged()
    }

    private fun refresh() {
        viewModel.getItems().observe(this, Observer {
            when (it) {
                is Resource.Loading -> refreshLayout.isRefreshing = true
                is Resource.Loaded -> refreshLayout.isRefreshing = false
                is Resource.Error -> Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    items.clear()
                    items.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun itemClicked(item: ShoppingItem) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(EXTRA_ID, item.id)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun deleteClicked(item: ShoppingItem) {
        viewModel.removeItem(item.id).observe(this, Observer { refresh() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETAILS && resultCode == RESULT_OK) {
            refresh()
        }
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