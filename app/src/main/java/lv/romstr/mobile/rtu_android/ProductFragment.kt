package lv.romstr.mobile.rtu_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment(), ProductDialogFragment.AlertDialogClickListener {

    private val items = mutableListOf(
        ShoppingItem("Bread", 1, "pcs."),
        ShoppingItem("Eggs", 10, "pcs."),
        ShoppingItem("Milk", 1, "l"),
        ShoppingItem("Potatoes", 2, "kg")
    )

    private lateinit var adapter: ShoppingItemRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShoppingItemRecyclerAdapter(items) {
            val dialogFragment = ProductDialogFragment(it)
            dialogFragment.setTargetFragment(this, 300)
            dialogFragment.show(activity!!.supportFragmentManager, "Fragment tag")
        }
        mainItems.adapter = adapter

        mainButtonAdd.setOnClickListener { appendItem() }
    }

    private fun appendItem() {
        items.add(ShoppingItem(mainEditName.text.toString(), 1, ""))
        items.sortBy { it.name }
        mainEditName.text.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onPositiveButtonClick(position: Int) {
        adapter.removeItem(position)
    }
}