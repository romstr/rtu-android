package lv.romstr.mobile.rtu_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_shopping.view.*

class ShoppingItemRecyclerAdapter(
    private val listener: AdapterClickListener,
    private val items: MutableList<ShoppingItem>
) :
    RecyclerView.Adapter<ShoppingItemRecyclerAdapter.ShoppingViewHolder>() {

    class ShoppingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shopping, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.itemView.shoppingName.text = item.name
        holder.itemView.shoppingQuantity.text = context.resources
            .getString(R.string.quantity_text, item.quantity, item.unit)

        holder.itemView.setOnClickListener {
            listener.itemClicked(items[position])
        }

        holder.itemView.shoppingRemove.setOnClickListener {
            listener.deleteClicked(items[position])
            items.removeAt(position)
            notifyDataSetChanged()
        }
    }
}