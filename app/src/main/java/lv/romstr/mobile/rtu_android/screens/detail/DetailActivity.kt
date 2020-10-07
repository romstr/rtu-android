package lv.romstr.mobile.rtu_android.screens.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_detail.*
import lv.romstr.mobile.rtu_android.R
import lv.romstr.mobile.rtu_android.ShoppingItem
import lv.romstr.mobile.rtu_android.api.FirebaseRepository
import lv.romstr.mobile.rtu_android.screens.main.MainActivity.Companion.EXTRA_ID

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra(EXTRA_ID).orEmpty()

        viewModel.getItem(id).observe(this, {
            detailsNameInput.setText(it.name)
            detailsCountInput.setText(it.quantity.toString())
            val units = resources.getStringArray(R.array.shopping_units)
            detailsUnitInput.setSelection(units.indexOfFirst { unit -> unit == it.unit })
        })

        detailsSave.setOnClickListener {
            viewModel.updateItem(
                ShoppingItem(
                    id = id,
                    name = detailsNameInput.text.toString(),
                    quantity = detailsCountInput.text.toString().toInt(),
                    unit = detailsUnitInput.selectedItem.toString()
                )
            )
            setResult(RESULT_OK)
            finish()
        }
    }
}