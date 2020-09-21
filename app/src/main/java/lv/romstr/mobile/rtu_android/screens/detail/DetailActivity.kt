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
import lv.romstr.mobile.rtu_android.api.Resource
import lv.romstr.mobile.rtu_android.screens.main.MainActivity.Companion.EXTRA_ID

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra(EXTRA_ID).orEmpty()
        viewModel.getItem(id).observe(this, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    detailsNameInput.setText(resource.data.name)
                    detailsCountInput.setText(resource.data.quantity.toString())
                    val units = resources.getStringArray(R.array.shopping_units)
                    detailsUnitInput.setSelection(units.indexOfFirst { it == resource.data.unit })
                }
                is Resource.Error -> showError(resource.message.orEmpty())
                is Resource.Loading -> showProgress()
                is Resource.Loaded -> hideProgress()
            }
        })

        detailsSave.setOnClickListener {
            viewModel.updateItem(
                id,
                ShoppingItem(
                    name = detailsNameInput.text.toString(),
                    quantity = detailsCountInput.text.toString().toInt(),
                    unit = detailsUnitInput.selectedItem.toString()
                )
            ).observe(this, Observer {
                when (it) {
                    is Resource.Success -> {
                        setResult(RESULT_OK)
                        finish()
                    }
                    is Resource.Loading -> showProgress()
                    is Resource.Loaded -> hideProgress()
                    is Resource.Error -> showError(it.message.orEmpty())
                }
            })
        }
    }

    private fun showProgress() {
        detailProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        detailProgress.visibility = View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}