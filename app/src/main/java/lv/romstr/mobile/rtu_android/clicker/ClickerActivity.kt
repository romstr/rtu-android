package lv.romstr.mobile.rtu_android.clicker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_clicker.*
import lv.romstr.mobile.rtu_android.R

class ClickerActivity : AppCompatActivity() {

    private lateinit var viewModel: ClickerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicker)

        viewModel = ViewModelProvider(this).get(ClickerViewModel::class.java)
        viewModel.clicks.observe(this, Observer { clicksCount ->
            clickerText.text = "$clicksCount"
        })
        viewModel.dividedByTen.observe(this, Observer { isDividedByTen ->
            if (isDividedByTen) showToast()
        })
//        viewModel.clicks = savedInstanceState?.getInt(CLICKS_EXTRA) ?: 0

        clickerButton.setOnClickListener { incrementClickCount() }
    }

    private fun incrementClickCount() {
        viewModel.incrementClicks()
        viewModel.divideByTen()
    }

    private fun showToast() {
        Toast.makeText(
            this,
            getString(R.string.clicker_toast, viewModel.clicks.value),
            Toast.LENGTH_SHORT
        ).show()

        viewModel.setToastShown()
    }

    companion object {
        private const val CLICKS_EXTRA = "lv.romstr.mobile.clicks"
    }
}