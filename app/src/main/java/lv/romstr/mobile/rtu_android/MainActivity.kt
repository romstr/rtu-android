package lv.romstr.mobile.rtu_android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendEmailButton.setOnClickListener { sendEmail() }

        secondActivityButton.setOnClickListener {
            Intent(
                this,
                SecondActivity::class.java
            ).also { startActivity(it) }
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddressView.text.toString()))
            putExtra(Intent.EXTRA_SUBJECT, subjectView.text.toString())
            putExtra(Intent.EXTRA_TEXT, textView.text.toString())
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}