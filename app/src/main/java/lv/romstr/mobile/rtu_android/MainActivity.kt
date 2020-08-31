package lv.romstr.mobile.rtu_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainButtonClicker.setOnClickListener {
            startActivity(Intent(this, ClickerActivity::class.java))
        }

        mainButtonChat.setOnClickListener {
            startActivity(Intent(this, ChatSendActivity::class.java))
        }
    }
}