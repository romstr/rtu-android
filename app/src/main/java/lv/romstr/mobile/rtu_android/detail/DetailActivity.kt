package lv.romstr.mobile.rtu_android.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_detail.*
import lv.romstr.mobile.rtu_android.MainActivity.Companion.EXTRA_ID
import lv.romstr.mobile.rtu_android.R
import lv.romstr.mobile.rtu_android.shopping.ShoppingItem

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel

    private lateinit var viewModelFactory: DetailViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModelFactory = DetailViewModelFactory(application, intent.getLongExtra(EXTRA_ID, 0))
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.shoppingItem.observe(this, Observer(::showShoppingItem))

        detailsSave.setOnClickListener {
//            db.shoppingItemDao().update(
//                item.copy(
//                    name = detailsNameInput.text.toString(),
//                    quantity = detailsCountInput.text.toString().toInt(),
//                    unit = detailsUnitInput.selectedItem.toString()
//                )
//            )
//            val intent = Intent().putExtra(EXTRA_ID, item.uid)
//            setResult(RESULT_OK, intent)
//            finish()
        }
    }

    private fun showShoppingItem(item: ShoppingItem) {
        detailsNameInput.setText(item.name)
        detailsCountInput.setText(item.quantity.toString())

        val units = resources.getStringArray(R.array.shopping_units)

        detailsUnitInput.setSelection(units.indexOfFirst { it == item.unit })
    }
}