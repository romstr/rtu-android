package lv.romstr.mobile.rtu_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val keepItems = RandomData.items

    private lateinit var adapter: KeepItemRecyclerAdapter
    private lateinit var layoutManager: StaggeredGridLayoutManager
    private val repository = CatRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager =
            StaggeredGridLayoutManager(
                resources.getInteger(R.integer.span_count), StaggeredGridLayoutManager.VERTICAL
            ).apply {
                gapStrategy = GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        mainItems.layoutManager = layoutManager
        adapter = KeepItemRecyclerAdapter(keepItems)
        mainItems.adapter = adapter

        mainButtonAddText.setOnClickListener { addKeepItem(RandomData.textItem) }
        mainButtonAddImage.setOnClickListener { addImageItem() }
        mainButtonAddRadio.setOnClickListener { addKeepItem(RandomData.radioItem) }
    }

    private fun addImageItem() {
        repository.getImage().observe(this, Observer {
            val item = KeepItemImage(it.url)
            addKeepItem(item)
        })
    }

    private fun addKeepItem(item: KeepItem) {
        keepItems.add(0, item)
        adapter.notifyItemInserted(0)
        mainItems.smoothScrollToPosition(0)
    }
}