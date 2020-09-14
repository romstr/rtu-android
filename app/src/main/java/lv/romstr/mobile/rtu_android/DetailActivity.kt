package lv.romstr.mobile.rtu_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*
import lv.romstr.mobile.rtu_android.MainActivity.Companion.EXTRA_ID

class DetailActivity : AppCompatActivity() {

//        private val db get() = (application as App).db
    private val db get() = Database.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getLongExtra(EXTRA_ID, 0)
        val item = db.shoppingItemDao().getItemById(id)

        detailsNameInput.setText(item.name)
        detailsCountInput.setText(item.quantity.toString())

        val units = resources.getStringArray(R.array.shopping_units)

        detailsUnitInput.setSelection(units.indexOfFirst { it == item.unit })

        detailsSave.setOnClickListener {
            db.shoppingItemDao().update(
                item.copy(
                    name = detailsNameInput.text.toString(),
                    quantity = detailsCountInput.text.toString().toInt(),
                    unit = detailsUnitInput.selectedItem.toString()
                )
            )
            val intent = Intent().putExtra(EXTRA_ID, item.uid)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}