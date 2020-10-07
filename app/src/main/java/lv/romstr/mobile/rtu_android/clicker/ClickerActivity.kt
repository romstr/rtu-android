package lv.romstr.mobile.rtu_android.clicker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_clicker.*
import lv.romstr.mobile.rtu_android.R

class ClickerActivity : AppCompatActivity() {

    private lateinit var viewModel: ClickerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicker)

        viewModel = ViewModelProvider(this).get(ClickerViewModel::class.java)
        viewModel.clicks = savedInstanceState?.getInt(CLICKS_EXTRA) ?: 0
        clickerText.text = "${viewModel.clicks}"

        clickerButton.setOnClickListener { incrementClickCount() }
    }

    private fun incrementClickCount() {
        viewModel.clicks++
        clickerText.text = "${viewModel.clicks}"

        if (viewModel.clicks % 10 == 0) {
            Toast.makeText(
                this,
                getString(R.string.clicker_toast, viewModel.clicks),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val CLICKS_EXTRA = "lv.romstr.mobile.clicks"
    }
}