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

    private lateinit var viewModelFactory: ClickerViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicker)

        viewModelFactory = ClickerViewModelFactory(intent.getIntExtra(CLICKS_EXTRA, 0))
        viewModel = ViewModelProvider(this, viewModelFactory).get(ClickerViewModel::class.java)
        viewModel.clicks.observe(this, Observer { clicksCount ->
            clickerText.text = "$clicksCount"
        })
        viewModel.dividedByTen.observe(this, Observer { isDividedByTen ->
            if (isDividedByTen) showToast()
        })

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
        const val CLICKS_EXTRA = "lv.romstr.mobile.clicks"
    }
}