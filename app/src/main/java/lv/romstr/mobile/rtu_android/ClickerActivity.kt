package lv.romstr.mobile.rtu_android

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_clicker.*

class ClickerActivity : AppCompatActivity() {

    private var clicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicker)

        clicks = getPreferences(Context.MODE_PRIVATE)
            .getInt(EXTRA_CLICKS, 0)
        updateClickerText()

        clickerButton.setOnClickListener { incrementClickCount() }
    }

    private fun incrementClickCount() {
        clicks++
        updateClickerText()

        getPreferences(Context.MODE_PRIVATE)
            .edit()
            .putInt(EXTRA_CLICKS, clicks)
            .apply()

        if (clicks % 10 == 0) {
            Toast.makeText(this, getString(R.string.clicker_toast, clicks), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateClickerText() {
        clickerText.text = "$clicks"
    }

    companion object {
        private const val EXTRA_CLICKS = "lv.romstr.mobile.extra.clicks"
    }
}