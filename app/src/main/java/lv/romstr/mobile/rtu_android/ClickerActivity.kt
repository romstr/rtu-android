package lv.romstr.mobile.rtu_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_clicker.*

class ClickerActivity : AppCompatActivity() {

    private var clicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicker)

        clickerButton.setOnClickListener { incrementClickCount() }
    }

    private fun incrementClickCount() {
        clicks++
        clickerText.text = "$clicks"

        if (clicks % 10 == 0) {
            Toast.makeText(this, getString(R.string.clicker_toast, clicks), Toast.LENGTH_SHORT)
                .show()
        }
    }
}